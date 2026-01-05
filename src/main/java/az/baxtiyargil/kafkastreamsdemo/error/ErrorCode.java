package az.baxtiyargil.kafkastreamsdemo.error;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String asString();

    HttpStatus status();

    String message();

}
