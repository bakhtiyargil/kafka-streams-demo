package az.baxtiyargil.kafkastreamsdemo.error.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    String asString();

    HttpStatus status();

    String message();

    RetryPolicy retryPolicy();

}
