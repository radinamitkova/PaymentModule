package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import bg.codeacademy.spring.paymentmodule.model.DutyHistory;

import java.math.BigDecimal;
import java.util.List;

public interface DutyHistoryService
{
  int saveRecord(DutyAccount dutyAccount, BigDecimal payment, String transactionRef);

  DutyHistory getByRecordId(Long recordId);

  List<DutyHistory> getAllDutyHistoryByAccountId(Long id);

  int deleteByRecordId(Long recordId);
}
