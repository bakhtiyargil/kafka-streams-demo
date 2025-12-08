package az.baxtiyargil.kafkastreamsdemo.configuration.properties;

public final class ApplicationConstants {

    public static final Long SERIAL_VERSION_UID = 1L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SECURITY_METHOD_NAME = "basicAuth";
    public static final String OVERDRAFT_LIMIT_REGISTER_ID = "4205";

    private ApplicationConstants() {
    }

    public static class Messaging {

        public static final String HEADER_X_RETRY_COUNT = "X-Retry-Count";
        public static final String HEADER_X_EVENT_TYPE = "X-Event-Type";
        public static final String ROUTER_BINDING_SUFFIX = "Router-in-";

        private Messaging() {
        }

        public static class TopicNames {

            public static final String FEE_ITEM = "fee-item";
            public static final String PAYMENT = "payment";
            public static final String FEE_ITEM_DLT = "fee-item-dlt";
            public static final String PAYMENT_DLT = "payment-dlt";

            private TopicNames() {
            }

        }

        public static class OutputChannel {

            public static final String FEE_ITEM = "sendFeeItemEvent-out-0";
            public static final String PAYMENT = "sendPaymentEvent-out-0";

            private OutputChannel() {
            }

        }

        public static class ConsumerFunctionNames {

            public static final String FEE_ITEM_RECEIVED_EVENT_CONSUMER = "feeItemReceivedEventConsumer";
            public static final String FEE_ITEM_CREATED_EVENT_CONSUMER = "feeItemCreatedEventConsumer";
            public static final String FEE_ITEM_VALIDATED_EVENT_CONSUMER = "feeItemValidatedEventConsumer";
            public static final String PAYMENT_CREATED_EVENT_CONSUMER = "paymentCreatedEventConsumer";

            private ConsumerFunctionNames() {
            }

        }

    }

}
