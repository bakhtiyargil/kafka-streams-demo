package az.baxtiyargil.kafkastreamsdemo.error.exception;

import az.baxtiyargil.kafkastreamsdemo.error.ValidationErrorCodes;
import lombok.Getter;
import java.io.Serial;

@Getter
public class ValidationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Object[] args;
    private final ValidationErrorCodes errorCode;

    public ValidationException(ValidationErrorCodes errorCode, Object... args) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.args = args;
    }
}
