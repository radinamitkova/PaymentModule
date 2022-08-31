package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.security.AppUser;
import com.worldline.sips.api.exception.IncorrectSealException;
import com.worldline.sips.model.InitializationResponse;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;

public interface PaymentService
{
  InitializationResponse initializeSession(AppUser client, BigDecimal amount) throws Exception;

  String processPaypageResponse(MultiValueMap<String, String> multiValueMap) throws IncorrectSealException;
}
