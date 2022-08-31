package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.model.DutyAccount;

import java.math.BigDecimal;

public interface DutyAccountService {

    void updateAccountDuty(DutyAccount dutyAccount, BigDecimal newDuty);

    DutyAccount getDutyAccountById(Long id);

    DutyAccount getDutyAccountByClientId(String userId);

    int deleteDutyAccountById(Long id);
}
