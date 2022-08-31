package bg.codeacademy.spring.paymentmodule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@SpringBootTest
public class EmailServiceTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  EmailService emailService;

  @Test
  public void testSendMessage()
  {
    emailService.inviteUnregisteredClient("denislav.delev@abv.bg");
//    emailService.sendInviteMessage("denislav.delev@abv.bg", "INVITE.TEST.TOKEN");
//    emailService.sendInviteMessage("momchil276@gmail.com", "loginPage");
//    emailService.sendInviteMessage("iki.bul@gmail.com", "loginPage");
//    emailService.sendInviteMessage("radina.mitkovaaaa@gmail.com", "loginPage");

//    emailService.sendActivateMessage("denislav.delev@abv.bg", "CONFIRMATION.TEST.TOKEN");
    emailService.sendConfirmationEmail("denislav.delev@abv.bg");
  }
}