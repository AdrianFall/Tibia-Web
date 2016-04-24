package core.repository.service.exception;

/**
 * Created by Adrian on 24/04/2016.
 */
public class PlayerDoesNotExistException extends Exception {
    public PlayerDoesNotExistException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PlayerDoesNotExistException(String message) {
        super(message);
    }

    public PlayerDoesNotExistException() {
        super();
    }
}
