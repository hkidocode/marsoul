package ma.youcode.marsoul.exception;

public class EmailNotExistException extends RuntimeException {
    public EmailNotExistException(String message) {
        super(message);
    }
}
