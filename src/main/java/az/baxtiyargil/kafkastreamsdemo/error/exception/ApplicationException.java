package az.baxtiyargil.kafkastreamsdemo.error.exception;

import az.baxtiyargil.kafkastreamsdemo.error.ApplicationErrorCodes;
import lombok.Getter;
import java.io.Serial;
import java.util.Map;

@Getter
public class ApplicationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, Object> args;
    private final ApplicationErrorCodes errorCode;

    public ApplicationException(ApplicationErrorCodes errorCode, Map<String, Object> args) {
        super(errorCode.name());
        this.errorCode = errorCode;
        this.args = args;
    }
}
