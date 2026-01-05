package az.baxtiyargil.kafkastreamsdemo.error;

import az.baxtiyargil.kafkastreamsdemo.error.exception.ApplicationException;
import az.baxtiyargil.kafkastreamsdemo.error.exception.ValidationException;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex) {
        String message = resolveMessage(ex.getErrorCode(), ex.getArgs());
        var response = buildErrorResponse(ex.getErrorCode(), message);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        String message = resolveApplicationMessage(ex.getErrorCode(), ex.getArgs());
        var response = buildErrorResponse(ex.getErrorCode(), message);
        return ResponseEntity.internalServerError().body(response);
    }

    private ErrorResponse buildErrorResponse(ErrorCode errorCode, String message) {
        return new ErrorResponse(
                UUID.randomUUID().toString(),
                errorCode.asString(),
                message,
                errorCode.status().value()
        );
    }

    private String resolveMessage(ErrorCode code, Object... args) {
        try {
            return messageSource.getMessage(code.message(), args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException exception) {
            return code.message();
        }
    }

    private String resolveApplicationMessage(ErrorCode code, Map<String, Object> args) {
        return args.isEmpty() ? code.message() :
                StringSubstitutor.replace(code.message(), args, "{", "}");
    }
}
