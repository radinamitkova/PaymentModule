package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.model.LoginDetails;

public interface LoginDetailsService {

    //boolean validatePassword(String password, String confirmPassword) throws Exception;

    //boolean validateUsername(String username) throws Exception;

    void createLoginDetails(String clientId, String username, String password) throws Exception;

    void activateLoginDetails(String authClientId, String token);

    LoginDetails getByUsername(String username);

    LoginDetails getByUserID(String clientID);

    LoginDetails getByUserEmail(String email);

    LoginDetails getByUserPhone(String phone);

    int deleteByLoginId(Long loginID);

    int deleteByUserId(Long clientID);
}
