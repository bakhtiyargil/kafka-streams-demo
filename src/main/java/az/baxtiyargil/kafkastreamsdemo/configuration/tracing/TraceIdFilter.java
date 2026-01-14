package az.baxtiyargil.kafkastreamsdemo.configuration.tracing;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import static az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants.Messaging.HEADER_X_TRACE_ID;

@Component
public class TraceIdFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceId = Optional.ofNullable(request.getHeader(HEADER_X_TRACE_ID))
                .orElse(UUID.randomUUID().toString());

        TraceContext.setTraceId(traceId);
        response.setHeader(HEADER_X_TRACE_ID, traceId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            TraceContext.clearTraceId();
        }
    }
}
