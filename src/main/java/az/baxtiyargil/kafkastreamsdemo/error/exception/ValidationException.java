package az.baxtiyargil.kafkastreamsdemo.error.exception;

import az.baxtiyargil.kafkastreamsdemo.error.ValidationErrorCodes;
import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final Object[] args;
    private final ValidationErrorCodes errorCode;

    public ValidationException(ValidationErrorCodes errorCode, Object... args) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.args = args;
    }
}
