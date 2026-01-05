package az.baxtiyargil.kafkastreamsdemo.error;

import lombok.Getter;
import org.apache.commons.text.StringSubstitutor;
import java.io.Serial;
import java.util.Map;

public class ApplicationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    @Getter
    private final ErrorCode code;

    public ApplicationException(ErrorCode code) {
        super(code.message());
        this.code = code;
    }

    public ApplicationException(ErrorCode code, Map<String, Object> arguments) {
        super(resolveMessage(code.message(), arguments));
        this.code = code;
    }

    private static String resolveMessage(String message, Map<String, Object> arguments) {
        return arguments.isEmpty() ? message :
                StringSubstitutor.replace(message, arguments, "{", "}");
    }
}
