package bg.codeacademy.spring.paymentmodule.service;

import java.util.List;
import java.util.Map;

public interface EmailService
{
  Map<String, List<String>> inviteUnregisteredClients();
  int inviteUnregisteredClient(String email);
  int sendConfirmationEmail(String email);
}
