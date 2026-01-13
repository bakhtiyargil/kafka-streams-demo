package az.baxtiyargil.kafkastreamsdemo.configuration;

import org.slf4j.MDC;

public final class TraceContext {

    public static final String TRACE_ID = "traceId";

    public static void setTraceId(String traceId) {
        MDC.put(TRACE_ID, traceId);
    }

    public static String getTraceId() {
        return MDC.get(TRACE_ID);
    }

    public static String popTraceId() {
        var traceId = MDC.get(TRACE_ID);
        MDC.remove(TRACE_ID);
        return traceId;
    }

    public static void clearTraceId() {
        MDC.remove(TRACE_ID);
    }
}

