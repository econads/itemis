package exercise.exceptions;

/**
 * General exception for this application.  Indicates that the application should terminate.
 */
public class BlockingException extends Exception {
    public BlockingException(String message) {
        super(message);
    }
}
