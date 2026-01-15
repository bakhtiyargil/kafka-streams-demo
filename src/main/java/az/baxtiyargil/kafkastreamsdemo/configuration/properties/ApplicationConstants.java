package az.baxtiyargil.kafkastreamsdemo.configuration.properties;

public final class ApplicationConstants {

    public static final Long SERIAL_VERSION_UID = 1L;

    private ApplicationConstants() {
    }

    public static class Messaging {

        public static final String HEADER_X_RETRY_COUNT = "X-Retry-Count";
        public static final String HEADER_X_EVENT_TYPE = "X-Event-Type";
        public static final String HEADER_X_TRACE_ID = "X-Trace-Id";
        public static final String ROUTER_BINDING_SUFFIX = "Router-in-";
        public static final String HEADER_X_RETRY_REASON = "X-Retry-REASON";

        private Messaging() {
        }

        public static class TopicNames {

            public static final String ORDER = "order";
            public static final String ORDER_DLT = "order.dlt";
            public static final String ORDER_RETRY = "order.retry";

            private TopicNames() {
            }

        }

        public static class OutputChannel {

            public static final String ORDER = "sendOrderEvent-out-0";
            public static final String ORDER_RETRY = "sendOrderRetryEvent-out-0";

            private OutputChannel() {
            }

        }

        public static class ConsumerFunctionNames {

            public static final String ORDER_CREATED_EVENT_CONSUMER = "orderCreatedEventConsumer";

            private ConsumerFunctionNames() {
            }

        }

    }

}
