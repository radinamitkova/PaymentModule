package bg.codeacademy.spring.paymentmodule.exception;

public class ElementAlreadyExistsException extends RuntimeException{

    public ElementAlreadyExistsException(String message) {
        super(message);
    }

}
