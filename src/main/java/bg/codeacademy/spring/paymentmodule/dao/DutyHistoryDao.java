package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.DutyHistory;

import java.util.List;

public interface DutyHistoryDao
{
  int saveRecord(DutyHistory record);

  DutyHistory getByRecordId(Long recordId);

  List<DutyHistory> getAllDutyHistoryByAccountId(Long id);

  int deleteByRecordId(Long recordId);
}
