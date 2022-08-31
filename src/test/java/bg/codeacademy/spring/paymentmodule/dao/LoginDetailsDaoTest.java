package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.exception.NoDataException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.LoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.testng.Assert.*;

@SpringBootTest
public class LoginDetailsDaoTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  LoginDetailsDao dao;
  @Autowired
  ClientDao clientDao;
  Client client;
  LoginDetails login;

  @BeforeClass
  public void beforeClass()
  {
    this.client = new Client("TestID1", "Test Client Name", "test@email.com", "+359 883 317 635");

    this.login = new LoginDetails();
    login.setClient(client);
    login.setUsername("testUsername");
    login.setPassword("Pass123");
  }

  @Test(priority = 1)
  public void testSaveLoginDetails()
  {
    clientDao.createClient(client);

    int status = dao.saveLoginDetails(login);
    assertEquals(status, 1);
  }

  @Test(priority = 2)
  public void testGetByUserID()
  {
    LoginDetails l = dao.getByUserID("TestID1");
    login.setLoginId(l.getLoginId()); //needed for delete test method

    assertEquals(l.getUsername(), "testUsername");
    assertEquals(l.getPassword(), "Pass123");
    assertEquals(l.getClient().getEmail(), "test@email.com");
  }

  @Test(priority = 2)
  public void testGetByUserEmail()
  {
    LoginDetails l = dao.getByUserEmail(client.getEmail());

    assertEquals(l.getUsername(), login.getUsername());
    assertEquals(l.getPassword(), login.getPassword());
    assertEquals(l.getClient().getEmail(), login.getClient().getEmail());
  }

  @Test(priority = 2)
  public void testGetByUserPhone()
  {
    LoginDetails l = dao.getByUserPhone(client.getPhoneNumber());

    assertEquals(l.getUsername(), login.getUsername());
    assertEquals(l.getPassword(), login.getPassword());
    assertEquals(l.getClient().getEmail(), login.getClient().getEmail());
  }

  @Test(priority = 2)
  public void isPresent(){
    assertNotNull(dao.getByUsername(login.getUsername()));
    assertNotNull(dao.getByUsername("notExistingUsername"));
  }

  @Test(priority = 3)
  public void testDelete()
  {
    int status = dao.deleteByLoginId(login.getLoginId());
    clientDao.deleteClient(client.getId());

    assertEquals(status, 1);
    assertThrows(NoDataException.class, () -> clientDao.getClient(client.getId()));
  }
}