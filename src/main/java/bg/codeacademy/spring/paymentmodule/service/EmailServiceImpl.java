package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.exception.BadRequestException;
import bg.codeacademy.spring.paymentmodule.exception.NoDataException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.Token;
import lombok.extern.apachecommons.CommonsLog;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Service
@CommonsLog
public class EmailServiceImpl implements EmailService
{
  private final JavaMailSender      emailSender;
  private final ClientService       clientService;
  private final TokenService        tokenService;
  private final LoginDetailsService loginService;

  public EmailServiceImpl(JavaMailSender e, ClientService c, TokenService t, LoginDetailsService l)
  {
    this.emailSender = e;
    this.clientService = c;
    this.tokenService = t;
    this.loginService = l;
  }

  private int sendInviteMessage(String email, String token){
    URI redirectUri = ServletUriComponentsBuilder
        .fromHttpUrl("http://drimpay.com/api/v1/register/" + token)
        .build().toUri();

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("payment.module.drim@abv.bg");
    message.setTo(email);
    message.setSubject("Registration Invitation");
    message.setText(
            "You are kindly invited to register for our service\n\n" +
            "on the following link:\n\n" +
            redirectUri
    );

    try {
      emailSender.send(message);
      log.info("Invitation email successfully sent to " + email);
      return 1;
    }
    catch (MailException e) {
      log.warn("Sending invitation email to " + email + " failed!");
      log.warn(e.getMessage());
      return 0;
    }
  }

  private int sendActivateMessage(String email, String token){
    URI redirectUri = ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/api/v1/login/" + token)
        .build().toUri();

    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom("payment.module.drim@abv.bg");
    message.setTo(email);
    message.setSubject("Confirmation Email");
    message.setText(
        "In order to verify your account\n\n" +
        "please follow the confirmation link:\n\n" +
        redirectUri
    );

    try {
      emailSender.send(message);
      log.info("Confirmation email successfully sent to " + email);
      return 1;
    }
    catch (MailException e) {
      log.warn("Sending confirmation email to " + email + " failed!");
      log.warn(e.getMessage());
      return 0;
    }
  }

  /** Sends invitation email, with newly generated registration token,
  * to all existing but not registered clients.
  * Return Lists of emails with successful and failed messages */
  @Override
  public Map<String, List<String>> inviteUnregisteredClients(){
    List<Client> clients = clientService.getClientsForFirstEmail();
    if (clients == null) throw new NoDataException("No unregistered clients present!");

    List<String> sent = new ArrayList<>();
    List<String> failed = new ArrayList<>();

    for (Client client: clients) {
      Token token = tokenService.generateToken(Token.TokenType.REGISTRATION, client.getId());
      int status = sendInviteMessage(client.getEmail(), token.getTokenBody());

      if(status == 1){
        sent.add(client.getEmail());
      } else {
        failed.add(client.getEmail());
      }
    }

    Map<String, List<String>> report = new HashMap<>();
    if ( !sent.isEmpty() ) report.put("sent", sent);
    if (!failed.isEmpty()) report.put("failed", failed);
    return report;
  }

  /** Sends invitation email, with newly generated registration token
   * to a single existing but not registered client.
   * Returns status 1 if succeeded and 0 if failed. */
  @Override
  public int inviteUnregisteredClient(String email){
    Client client = clientService.getClientByEmail(email);

    if(loginService.getByUserEmail(email) != null){
      throw new BadRequestException("This user is already registered and confirmed!");
    }

    Token token = tokenService.generateToken(Token.TokenType.REGISTRATION, client.getId());

    return sendInviteMessage(email, token.getTokenBody());
  }

  /** Sends activation email, with newly generated activation token
   * to a client with existing LoginDetails (password, username).
   * Returns status 1 if succeeded and 0 if failed. */
  @Override
  public int sendConfirmationEmail(String email)
  {
    Client client = clientService.getClientByEmail(email);
    
    Token token = tokenService.generateToken(Token.TokenType.ACTIVATION, client.getId());

    return sendActivateMessage(email, token.getTokenBody());
  }

}
