package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.TopicNames;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.cloud.stream.binder.kafka.ListenerContainerWithDlqAndRetryCustomizer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.BackOff;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class ListenerContainerDlqAndRetryCustomizer implements ListenerContainerWithDlqAndRetryCustomizer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void configure(AbstractMessageListenerContainer<?, ?> container,
                          String destinationName,
                          String group,
                          BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> dlqDestinationResolver,
                          BackOff backOff) {
        if (destinationName.equals(TopicNames.FEE_ITEM_DLT) || destinationName.equals(TopicNames.PAYMENT_DLT)) {
            ConsumerRecordRecoverer dlpr = new RetryDeadLetterPublishingRecoverer(kafkaTemplate,
                    dlqDestinationResolver);
            container.setCommonErrorHandler(new DefaultErrorHandler(dlpr, backOff));
        }
    }

    @Override
    public boolean retryAndDlqInBinding(String destinationName, String group) {
        return !(destinationName.equals(TopicNames.FEE_ITEM_DLT) || destinationName.equals(TopicNames.PAYMENT_DLT));
    }

}
