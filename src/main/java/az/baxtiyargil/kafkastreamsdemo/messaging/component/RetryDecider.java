package az.baxtiyargil.kafkastreamsdemo.messaging.component;

import az.baxtiyargil.kafkastreamsdemo.configuration.properties.ApplicationConstants;
import az.baxtiyargil.kafkastreamsdemo.configuration.properties.MessagingProperties;
import az.baxtiyargil.kafkastreamsdemo.error.exception.ApplicationException;
import az.baxtiyargil.kafkastreamsdemo.error.exception.RetryPolicy;
import az.baxtiyargil.kafkastreamsdemo.error.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;
import static reactor.core.Exceptions.unwrap;

@Component
@RequiredArgsConstructor
public class RetryDecider {

    private final MessagingProperties messagingProperties;

    public boolean isRetryable(Throwable t, Message<?> message) {
        int retryCount = (Integer) message.getHeaders().getOrDefault(ApplicationConstants.Messaging.HEADER_X_RETRY_COUNT, 0);
        if (retryCount >= messagingProperties.getMaxRetry()) {
            return false;
        }

        Throwable root = unwrap(t);
        if (root instanceof ApplicationException ex) {
            return RetryPolicy.RETRYABLE.equals(ex.getErrorCode().retryPolicy());
        } else if (root instanceof ValidationException ex) {
            return RetryPolicy.RETRYABLE.equals(ex.getErrorCode().retryPolicy());
        }
        return root instanceof TimeoutException
                || root instanceof SQLException
                || root instanceof TransientDataAccessException;
    }
}
