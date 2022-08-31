package bg.codeacademy.spring.paymentmodule.exception;

public class PaymentFailedException extends RuntimeException
{
  public PaymentFailedException(String message)
  {
    super(message);
  }
}
