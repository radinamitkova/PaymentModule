package bg.codeacademy.spring.paymentmodule.exception;

public class NoDataException extends RuntimeException
{
  public NoDataException(String message)
  {
    super(message);
  }
}
