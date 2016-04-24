package core.repository.service.exception;

/**
 * Created by Adrian on 24/04/2016.
 */
public class AccountDoesNotExistException extends Exception {
    public AccountDoesNotExistException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AccountDoesNotExistException(String message) {
        super(message);
    }

    public AccountDoesNotExistException() {
        super();
    }
}
