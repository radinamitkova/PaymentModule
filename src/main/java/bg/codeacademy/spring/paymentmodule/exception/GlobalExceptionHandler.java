package bg.codeacademy.spring.paymentmodule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
  private Map<String, String> getMap(String message) {

    Map<String, String> errors = new HashMap<>();
    errors.put("timestamp: ", LocalDateTime.now().toString());
    errors.put("message: ", message);

    return errors;
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Map<String, String>> handleNoSuchElementException(NoSuchElementException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoDataException.class)
  public ResponseEntity<Map<String, String>> handleNoDataException(NoDataException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExpiredTokenException.class)
  public ResponseEntity<Map<String, String>> handleExpiredTokenException(ExpiredTokenException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidDataException.class)
  public ResponseEntity<Map<String, String>> handleInvalidDataException(InvalidDataException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ElementAlreadyExistsException.class)
  public ResponseEntity<Map<String, String>> handleElementAlreadyExistsException(ElementAlreadyExistsException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
      BadRequestException.class,
      ValidationException.class
  })
  public ResponseEntity<Map<String, String>> handleBadRequestException(BadRequestException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PaymentFailedException.class)
  public ResponseEntity<Map<String, String>> handlePaymentFailedException(PaymentFailedException exception)
  {
    Map<String, String> errors = getMap(exception.getMessage());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
