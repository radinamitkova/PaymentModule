package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.exception.BadRequestException;
import bg.codeacademy.spring.paymentmodule.exception.PaymentFailedException;
import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import bg.codeacademy.spring.paymentmodule.security.AppUser;
import com.worldline.sips.api.PaypageClient;
import com.worldline.sips.api.configuration.Environment;
import com.worldline.sips.api.exception.IncorrectSealException;
import com.worldline.sips.model.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService
{
  private final DutyAccountService dutyAccountService;
  private final DutyHistoryService dutyHistoryService;
  private       PaypageClient      paypageClient;

  public PaymentServiceImpl(DutyAccountService dutyAccountService, DutyHistoryService dutyHistoryService)
  {
    this.dutyAccountService = dutyAccountService;
    this.dutyHistoryService = dutyHistoryService;
  }

  public InitializationResponse initializeSession(AppUser appUser, BigDecimal amount) throws Exception
  {
    if (!appUser.getLoginDetails().isActive()) {
      throw new BadRequestException("Your account is not activated, please check your email.");
    }

    DutyAccount dutyAccount = dutyAccountService
        .getDutyAccountByClientId(appUser.getClient().getId());

    validatePaymentAmount(amount, dutyAccount);

    final String MERCHANT_ID = "002001000000002";
    final String SECRET_KEY = "002001000000002_KEY1";
    final Integer KEY_VERSION = 1;

    paypageClient = new PaypageClient(Environment.SIMU, MERCHANT_ID, KEY_VERSION, SECRET_KEY);

    amount = getSmallestUnitAmount(amount);
    PaymentRequest paymentRequest = getPaymentRequest(dutyAccount, amount);

    InitializationResponse initializationResponse = paypageClient.initialize(paymentRequest);
    RedirectionStatusCode redirectionStatusCode = initializationResponse.getRedirectionStatusCode();
    URL redirectionUrl = initializationResponse.getRedirectionUrl();

    if (redirectionUrl != null && redirectionStatusCode.getCode().equals("00")) {
      return initializationResponse;
    }
    else {
      throw new BadRequestException("Invalid request. Error code "
          + initializationResponse.getRedirectionStatusCode().getCode() + ": "
          + initializationResponse.getRedirectionStatusCode().name());
    }
  }

  public String processPaypageResponse(MultiValueMap<String, String> multiValueMapParameters)
  {
    Map<String, String> mappedRequestParameters = new HashMap<>();
    mappedRequestParameters.put("Data", multiValueMapParameters.getFirst("Data"));
    mappedRequestParameters.put("Seal", multiValueMapParameters.getFirst("Seal"));

    PaypageResponse paypageResponse;
    try {
      paypageResponse = paypageClient.decodeResponse(mappedRequestParameters);
    }
    catch (IncorrectSealException exception) {
      exception.printStackTrace();
      throw new PaymentFailedException("Payment failed! The request has been tampered with!");
    }

    if (!paypageResponse.getData().getResponseCode().getCode().equals("00")) {
      throw new PaymentFailedException("Payment failed! " + paypageResponse.getData().getAcquirerResponseCode().name());
    }

    String clientId = paypageResponse.getData().getCustomerId();
    DutyAccount dutyAccount = dutyAccountService.getDutyAccountByClientId(clientId);
    String paymentDetails = updateAccountDuty(paypageResponse, dutyAccount);

    createPaymentHistoryRecord(paypageResponse, dutyAccount);

    return "Payment Successful! " + paymentDetails;
  }

  private String updateAccountDuty(PaypageResponse paypageResponse, DutyAccount dutyAccount)
  {
    BigDecimal amount = getOriginalAmount(paypageResponse.getData().getAmount());

    dutyAccountService.updateAccountDuty(dutyAccount, amount);

    return "| Amount paid: " + amount + dutyAccount.getCurrency().name()
        + " | Current account duty: " + dutyAccount.getDuty()
        + " | Transaction ID: " + paypageResponse.getData().getTransactionReference();
  }

  private void createPaymentHistoryRecord(PaypageResponse paypageResponse, DutyAccount dutyAccount)
  {
    BigDecimal amount = getOriginalAmount(paypageResponse.getData().getAmount());

    String transactionReference = paypageResponse.getData().getTransactionReference();
    dutyHistoryService.saveRecord(dutyAccount, amount, transactionReference);
  }

  private PaymentRequest getPaymentRequest(DutyAccount dutyAccount, BigDecimal amount) throws MalformedURLException
  {
    final URL NORMAL_RETURN_URL = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/api/v1/payment/response")
        .build().toUri().toURL();

    PaymentRequest paymentRequest = new PaymentRequest();
    paymentRequest.setMerchantId("002001000000002");
    paymentRequest.setKeyVersion(1);
    paymentRequest.setAmount(amount.intValue());
    paymentRequest.setCurrencyCode(Currency.valueOf(dutyAccount.getCurrency().toString()));
    paymentRequest.setOrderChannel(OrderChannel.INTERNET);
    paymentRequest.setNormalReturnUrl(NORMAL_RETURN_URL);
    paymentRequest.setAutomaticResponseUrl(NORMAL_RETURN_URL);
    paymentRequest.setCustomerLanguage(Language.ENGLISH);
    paymentRequest.setCustomerId(dutyAccount.getClientId());

    return paymentRequest;
  }

  private BigDecimal getOriginalAmount(int paymentAmount)
  {
    /*Amount is received in the smallest currency unit,
    then restored in original amount (e.g. from 10523 to 105.23)*/
    BigDecimal amount = BigDecimal.valueOf(paymentAmount);
    amount = amount.divide(BigDecimal.valueOf(100));
    return amount;
  }

  private BigDecimal getSmallestUnitAmount(BigDecimal amount)
  {
    /*Amount is transmitted in the smallest currency unit.
    EUR 10.50 is transmitted in the form 1050.*/
    amount = amount.multiply(BigDecimal.valueOf(100));
    amount = amount.setScale(0, RoundingMode.HALF_UP);
    return amount;
  }

  private void validatePaymentAmount(BigDecimal amount, DutyAccount dutyAccount)
  {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new BadRequestException("Payment amount has to be a positive number");
    }
    if (dutyAccount.getDuty().compareTo(amount) < 0) {
      throw new BadRequestException("Payment amount is greater than duty!");
    }
  }
}