package core.repository.service.exception;

/**
 * Created by Adrian on 24/04/2016.
 */
public class PlayerExistsInHuntedListException extends Exception {

    public PlayerExistsInHuntedListException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PlayerExistsInHuntedListException(String message) {
        super(message);
    }

    public PlayerExistsInHuntedListException() {
        super();
    }
}
