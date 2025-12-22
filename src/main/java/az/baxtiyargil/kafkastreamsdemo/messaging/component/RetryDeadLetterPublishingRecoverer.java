package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import jakarta.validation.constraints.NotNull;
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

@Slf4j
public class RetryDeadLetterPublishingRecoverer extends DeadLetterPublishingRecoverer {

    public RetryDeadLetterPublishingRecoverer(KafkaOperations<?, ?> template,
                                              BiFunction<ConsumerRecord<?, ?>,
                                                      Exception, TopicPartition> destinationResolver) {
        super(template, destinationResolver);
    }

    protected void publish(@NotNull ProducerRecord<Object, Object> outRecord,
                           @NotNull KafkaOperations<Object, Object> kafkaTemplate,
                           @NotNull ConsumerRecord<?, ?> inRecord) {

        Headers inHeaders = inRecord.headers();
        Header retryHeader = inHeaders.lastHeader(Messaging.HEADER_X_RETRY_COUNT);

        int retryCount = retryHeader == null
                ? 1
                : Integer.parseInt(new String(retryHeader.value())) + 1;
        if (retryCount > 3) {
            log.warn("Retries exceeded, ignoring message {}", inRecord.value());
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
