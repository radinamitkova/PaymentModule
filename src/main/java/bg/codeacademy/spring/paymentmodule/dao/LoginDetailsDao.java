package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.LoginDetails;

public interface LoginDetailsDao
{
  int saveLoginDetails(LoginDetails l);

  int updateLoginDetails(LoginDetails loginDetails);

  LoginDetails getByUsername(String username);

  LoginDetails getByUserID(String clientID);

  LoginDetails getByUserEmail(String email);

  LoginDetails getByUserPhone(String phone);

  int deleteByLoginId(Long loginID);

  int deleteByUserId(Long clientID);
}
