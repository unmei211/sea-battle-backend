package it.sevenbits.seabattle.core.validator.session;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * bad valid exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadValidException extends RuntimeException {

    /**
     * exception with cause
     *
     * @param message - message
     * @param cause   - cause
     */
    public BadValidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * default method
     *
     * @param message - message
     */
    public BadValidException(final String message) {
        super(message);
    }

}

