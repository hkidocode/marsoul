package ma.youcode.marsoul.exception;

public class ExceedLoginAttemptsException extends RuntimeException {
    public ExceedLoginAttemptsException(String message) {
        super(message);
    }
}
