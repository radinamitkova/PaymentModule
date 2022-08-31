package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.dao.DutyAccountDao;
import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DutyAccountServiceImpl implements DutyAccountService {

    private final DutyAccountDao dutyAccountDao;

    public DutyAccountServiceImpl(DutyAccountDao dutyAccountDao) {
        this.dutyAccountDao = dutyAccountDao;
    }

  @Override
  public void updateAccountDuty(DutyAccount dutyAccount, BigDecimal paymentAmount)
  {
    dutyAccount.setDuty(dutyAccount.getDuty().subtract(paymentAmount));
    this.dutyAccountDao.updateDutyAccount(dutyAccount);
  }

    @Override
    public DutyAccount getDutyAccountById(Long id) {
        return this.dutyAccountDao.getDutyAccountById(id);
    }

    @Override
    public DutyAccount getDutyAccountByClientId(String userId) {
        return this.dutyAccountDao.getDutyAccountByClientId(userId);
    }

    @Override
    public int deleteDutyAccountById(Long id) {
        return this.dutyAccountDao.deleteDutyAccountById(id);
    }
}
