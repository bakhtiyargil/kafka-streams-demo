package az.baxtiyargil.kafkastreamsdemo.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus status();

    String message();

}
