package ma.youcode.marsoul.exception;

public class PersonExistException extends RuntimeException {
    public PersonExistException(String message) {
        super(message);
    }
}
