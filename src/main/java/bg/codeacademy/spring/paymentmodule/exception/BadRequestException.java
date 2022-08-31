package bg.codeacademy.spring.paymentmodule.exception;

public class BadRequestException extends RuntimeException
{
  public BadRequestException(String message)
  {
    super(message);
  }
}
