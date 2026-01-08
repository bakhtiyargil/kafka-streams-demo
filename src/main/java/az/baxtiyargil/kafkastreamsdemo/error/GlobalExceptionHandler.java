package az.baxtiyargil.kafkastreamsdemo.error;

import az.baxtiyargil.kafkastreamsdemo.error.exception.ApplicationException;
import az.baxtiyargil.kafkastreamsdemo.error.exception.ValidationException;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
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
        var errId = UUID.randomUUID().toString();
        String message = resolveMessage(ex.getErrorCode(), ex.getArgs());
        var response = buildErrorResponse(errId, ex.getErrorCode().name(), message, ex.getErrorCode().status().value());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        var errId = UUID.randomUUID().toString();
        String message = resolveApplicationMessage(ex.getErrorCode(), ex.getArgs());
        var response = buildErrorResponse(errId, ex.getErrorCode().name(), message, ex.getErrorCode().status().value());
        return ResponseEntity.internalServerError().body(response);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String errId = UUID.randomUUID().toString();

        ErrorResponse response = new ErrorResponse(
                errId,
                ValidationErrorCodes.VALIDATION_ERROR.name(),
                ValidationErrorCodes.VALIDATION_ERROR.message(),
                HttpStatus.BAD_REQUEST.value()
        );

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> response.addValidationError(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ));

        return ResponseEntity.badRequest().body(response);
    }

    private ErrorResponse buildErrorResponse(String id, String errorCode, String message, Integer status) {
        return new ErrorResponse(
                id,
                errorCode,
                message,
                status
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
