package bg.codeacademy.spring.paymentmodule.exception;

public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

}