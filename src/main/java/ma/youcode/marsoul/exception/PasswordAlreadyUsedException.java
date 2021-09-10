package ma.youcode.marsoul.exception;

public class PasswordAlreadyUsedException extends RuntimeException {
    public PasswordAlreadyUsedException(String message) {
        super(message);
    }
}
