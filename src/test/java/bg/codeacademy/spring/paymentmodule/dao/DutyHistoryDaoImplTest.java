package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import bg.codeacademy.spring.paymentmodule.model.DutyHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.testng.Assert.*;

@SpringBootTest
public class DutyHistoryDaoImplTest extends AbstractTestNGSpringContextTests
{
  @Autowired
  DutyHistoryDaoImpl dao;
  DutyHistory rec;
  LocalDateTime time = LocalDateTime.now();

  @BeforeClass
  public void beforeClass()
  {
    DutyAccount acc = new DutyAccount("11client1", DutyAccount.CurrencyType.EUR);
    acc.setId(1L);
    acc.setDuty(BigDecimal.valueOf(1050));

    rec = new DutyHistory();
    rec.setAccount(acc);
    rec.setPayment(BigDecimal.valueOf(250.56));
    rec.setTime(time);
  }

  @Test(priority = 1)
  public void testSaveRecord()
  {
    int status = dao.saveRecord(rec);
    assertEquals(status, 1);
  }

  @Test(priority = 2)
  public void testGetAllByAccountId(){
    Long testBankAccountId = 1L;

    List<DutyHistory> actual = dao.getAllDutyHistoryByAccountId(testBankAccountId);
    assertNotNull(actual);

    long lastId = 0L;
    for(DutyHistory rec : actual){
      assertEquals(rec.getAccount().getId(), testBankAccountId);
      lastId = Math.max(rec.getRecordId(), lastId);
    }

    rec.setRecordId(lastId); //needed for delete test
  }

  @Test(priority = 3)
  public void testGetByHistoryId(){
    DutyHistory actual = dao.getByRecordId(rec.getRecordId());

    assertEquals(actual.getRecordId(), rec.getRecordId());
    assertEquals(actual.getAccount().getId(),
        rec.getAccount().getId());
    assertEquals(actual.getPayment(), rec.getPayment());
    assertEquals(actual.getTime().toLocalDate(), rec.getTime().toLocalDate());
  }

  @Test(priority = 4)
  public void testDeleteByRecordId()
  {
    int status = dao.deleteByRecordId(rec.getRecordId());

    assertEquals(status, 1);
  }
}