package ma.youcode.marsoul.exception;

public class PersonNotExistException extends RuntimeException {

    public PersonNotExistException(String message) {
        super(message);
    }
}
