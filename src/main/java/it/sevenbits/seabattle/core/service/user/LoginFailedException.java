package it.sevenbits.seabattle.core.service.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * exception
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoginFailedException extends RuntimeException {

    /**
     * exception with cause
     *
     * @param message - message
     * @param cause   - cause
     */
    public LoginFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * default method
     *
     * @param message - message
     */
    public LoginFailedException(final String message) {
        super(message);
    }

}

