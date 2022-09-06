package exceptions;

public class InvalidParameterException extends Exception {
    public InvalidParameterException() {}

    public InvalidParameterException(String message) {
        super(message);
    }

    public InvalidParameterException(Throwable cause) {
        super(cause);
    }
}
