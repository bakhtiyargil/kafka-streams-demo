package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.MessagingProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import java.util.function.BiFunction;
import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging;
import org.springframework.lang.NonNull;

@Slf4j
public class RetryDeadLetterPublishingRecoverer extends DeadLetterPublishingRecoverer {

    private final MessagingProperties messagingProperties;

    public RetryDeadLetterPublishingRecoverer(KafkaOperations<?, ?> template,
                                              BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> destinationResolver,
                                              MessagingProperties messagingProperties) {
        super(template, destinationResolver);
        this.messagingProperties = messagingProperties;
    }

    protected void publish(@NonNull ProducerRecord<Object, Object> outRecord,
                           @NonNull KafkaOperations<Object, Object> kafkaTemplate,
                           @NonNull ConsumerRecord<?, ?> inRecord) {

        Headers inHeaders = inRecord.headers();
        Header retryHeader = inHeaders.lastHeader(Messaging.HEADER_X_RETRY_COUNT);

        int retryCount = retryHeader == null
                ? 1
                : Integer.parseInt(new String(retryHeader.value())) + 1;
        if (retryCount > Integer.parseInt(messagingProperties.getMaxDltRetry())) {
            log.warn("Retries exceeded, ignoring message key: {}", inRecord.key());
            return;
        }

        inHeaders.remove(Messaging.HEADER_X_RETRY_COUNT);
        inHeaders.add(
                Messaging.HEADER_X_RETRY_COUNT,
                Integer.toString(retryCount).getBytes()
        );

        inHeaders.forEach(h -> outRecord.headers().add(h));
        kafkaTemplate.send(outRecord);
    }

}
