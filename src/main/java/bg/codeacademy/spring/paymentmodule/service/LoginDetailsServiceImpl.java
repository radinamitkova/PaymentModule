package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.dao.LoginDetailsDao;
import bg.codeacademy.spring.paymentmodule.exception.BadRequestException;
import bg.codeacademy.spring.paymentmodule.exception.ElementAlreadyExistsException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.LoginDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginDetailsServiceImpl implements LoginDetailsService {

    private final LoginDetailsDao loginDetailsDao;
    private final ClientService   clientService;
    private final TokenService    tokenService;
    private final PasswordEncoder passwordEncoder;

    public LoginDetailsServiceImpl(LoginDetailsDao loginDetailsDao,
                                   ClientService clientService,
                                   TokenService tokenService,
                                   PasswordEncoder passwordEncoder) {
        this.loginDetailsDao = loginDetailsDao;
        this.clientService = clientService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createLoginDetails(String clientId, String username, String password) {
        Client client = this.clientService.getClientByID(clientId);

        LoginDetails newLoginDetails = new LoginDetails();
        newLoginDetails.setClient(client);
        newLoginDetails.setUsername(username);
        newLoginDetails.setActive(false);
        newLoginDetails.setPassword(passwordEncoder.encode(password));

        int status = loginDetailsDao.saveLoginDetails(newLoginDetails);
        if (status == 0){
            throw new ElementAlreadyExistsException("This client is already registered!");
        }
    }

    @Override
    public LoginDetails getByUsername(String username)
    {
        return this.loginDetailsDao.getByUsername(username);
    }

    @Override
    public LoginDetails getByUserID(String clientID) {
        return this.loginDetailsDao.getByUserID(clientID);
    }

    @Override
    public LoginDetails getByUserEmail(String email) {
        return this.loginDetailsDao.getByUserEmail(email);
    }

    @Override
    public LoginDetails getByUserPhone(String phone) {
        return this.loginDetailsDao.getByUserPhone(phone);
    }

    @Override
    public int deleteByLoginId(Long loginID) {
        return this.loginDetailsDao.deleteByLoginId(loginID);
    }

    @Override
    public int deleteByUserId(Long clientID) {
        return this.loginDetailsDao.deleteByUserId(clientID);
    }

    @Override
    public void activateLoginDetails(String authClientId, String token){
        String tokenClientId = tokenService.getClientIdByTokenBody(token);

        if (!tokenClientId.equals(authClientId)){
            throw new BadRequestException("Error in provided data");
        }

        LoginDetails login = getByUserID(authClientId);

        if (!login.isActive()){
            login.setActive(true);
            loginDetailsDao.updateLoginDetails(login);
        }
    }
}
