
package bg.codeacademy.spring.paymentmodule;

import bg.codeacademy.spring.paymentmodule.service.EmailService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main
{
  public static EmailService emailService;

  public Main(EmailService emailService)
  {
    Main.emailService = emailService;
  }

  public static void sendFirstEmail()
  {
    emailService.inviteUnregisteredClients();
  }


  public static void main(String[] args)
  {
    SpringApplication.run(Main.class, args);

    sendFirstEmail();
  }
}