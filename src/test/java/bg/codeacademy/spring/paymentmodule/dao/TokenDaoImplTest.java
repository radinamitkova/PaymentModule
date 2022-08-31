package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.exception.NoDataException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

@SpringBootTest
public class TokenDaoImplTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  private TokenDao tokenDao;

  @Autowired
  private ClientDao clientDao;

  private Token  token;
  private Client client;

  @BeforeClass
  public void beforeClass()
  {
    client = clientDao.getClient("client1");
  }

  @Test(priority = 2)
  public void getToken_ReturnsToken_IfIdExists()
  {
    Token actual = tokenDao.getTokenById(1);

    assertEquals(actual.getTokenBody(), "ABC123");
  }

  @Test(expectedExceptions = NoDataException.class)
  public void getToken_ThrowsException_IfIdDoesntExist()
  {
    tokenDao.getTokenById(-123);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void saveToken_ThrowsException_IfTokenTypeIsInvalid()
  {
    Token badToken = new Token("123ABC", Token.TokenType.valueOf("Invalid token"), client);
    tokenDao.saveToken(badToken);
  }
}
