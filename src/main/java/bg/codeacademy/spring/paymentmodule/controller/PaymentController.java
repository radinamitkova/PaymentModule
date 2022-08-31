package bg.codeacademy.spring.paymentmodule.controller;

import bg.codeacademy.spring.paymentmodule.security.AppUser;
import bg.codeacademy.spring.paymentmodule.security.LoggedInUser;
import bg.codeacademy.spring.paymentmodule.service.PaymentService;
import com.worldline.sips.api.exception.IncorrectSealException;
import com.worldline.sips.model.InitializationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController
{
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService)
  {
    this.paymentService = paymentService;
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<?> initializeSession(@LoggedInUser AppUser client, @RequestParam BigDecimal amount) throws Exception
  {
    InitializationResponse response = paymentService.initializeSession(client, amount);

    String payPageUrl = response.getRedirectionUrl()
        + "?redirectionData=" + response.getRedirectionData()
        + "&seal=" + response.getSeal();

    return ResponseEntity.status(HttpStatus.FOUND)
        .body(payPageUrl);
  }

  @RequestMapping(value = "/response",
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public String receiveResponse(@RequestParam MultiValueMap<String, String> multiValueMapParameters) throws IncorrectSealException
  {
    return paymentService.processPaypageResponse(multiValueMapParameters);
  }
}
