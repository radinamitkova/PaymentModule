package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.DutyAccount;

import java.util.List;

public interface DutyAccountDao
{
    void updateDutyAccount(DutyAccount dutyAccount);

    DutyAccount getDutyAccountById(Long id);

    DutyAccount getDutyAccountByClientId(String clientId);

    int deleteDutyAccountById(Long id);

    List<DutyAccount> getAll();

}
