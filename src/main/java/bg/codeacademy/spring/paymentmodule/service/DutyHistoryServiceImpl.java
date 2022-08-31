package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.dao.DutyHistoryDao;
import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import bg.codeacademy.spring.paymentmodule.model.DutyHistory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DutyHistoryServiceImpl implements DutyHistoryService
{
  DutyHistoryDao historyDao;

  public DutyHistoryServiceImpl(DutyHistoryDao historyDao)
  {
    this.historyDao = historyDao;
  }

  @Override
  public int saveRecord(DutyAccount dutyAccount, BigDecimal payment, String transactionRef)
  {
    DutyHistory record = new DutyHistory();
    record.setAccount(dutyAccount);
    record.setPayment(payment);
    record.setTime(LocalDateTime.now());
    record.setTransactionReference(transactionRef);

    return historyDao.saveRecord(record);
  }

  @Override
  public DutyHistory getByRecordId(Long recordId)
  {
    return historyDao.getByRecordId(recordId);
  }

  @Override
  public List<DutyHistory> getAllDutyHistoryByAccountId(Long id)
  {
    return historyDao.getAllDutyHistoryByAccountId(id);
  }

  @Override
  public int deleteByRecordId(Long recordId)
  {
    return historyDao.deleteByRecordId(recordId);
  }
}
