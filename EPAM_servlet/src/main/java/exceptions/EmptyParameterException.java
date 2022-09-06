package exceptions;

public class EmptyParameterException extends Exception {
    public EmptyParameterException() {}

    public EmptyParameterException(String message) {
        super(message);
    }

    public EmptyParameterException(Throwable cause) {
        super(cause);
    }
}
