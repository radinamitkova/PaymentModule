package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.dao.ClientDao;
import bg.codeacademy.spring.paymentmodule.dao.TokenDao;
import bg.codeacademy.spring.paymentmodule.exception.ExpiredTokenException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

import static org.testng.Assert.assertEquals;

@SpringBootTest
public class TokenServiceImplTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  private TokenDao tokenDao;

  @Autowired
  private TokenService tokenService;

  @Autowired
  private ClientDao clientDao;

  private Token  token;
  private Client client;

  @BeforeClass
  public void beforeClass()
  {

    client = clientDao.getClient("client1");
  }

  @Test
  public void generateToken_ReturnsToken()
  {
    Token newToken = tokenService.generateToken(Token.TokenType.REGISTRATION, "client1");

    assertEquals(newToken.getTokenBody(), tokenDao.getTokenByTokenBody(newToken.getTokenBody()).getTokenBody());
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void generateToken_ThrowsException_IfClientDoesntExist()
  {
    Token newToken = tokenService.generateToken(Token.TokenType.REGISTRATION, "Non-existent client");
  }

  @Test
  public void getClientIdByTokenBody_ReturnsClientId()
  {
    Token newToken = tokenService.generateToken(Token.TokenType.ACTIVATION, "client1");
    String clientId = tokenService.getClientIdByTokenBody(newToken.getTokenBody());

    assertEquals(clientId, newToken.getClient().getId());
  }

  @Test(expectedExceptions = ExpiredTokenException.class)
  public void getClientIdByTokenBody_ThrowsException_IfTokenExpired()
  {
    Token newToken = tokenDao.getTokenById(12);
    tokenService.getClientIdByTokenBody(newToken.getTokenBody());
  }
}
