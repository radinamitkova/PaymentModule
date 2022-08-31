package bg.codeacademy.spring.paymentmodule.security;

import bg.codeacademy.spring.paymentmodule.dao.ClientDao;
import bg.codeacademy.spring.paymentmodule.dao.LoginDetailsDaoImpl;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.LoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{

  @Autowired
  ClientDao clientDao;

  @Autowired
  LoginDetailsDaoImpl loginDetailsDao;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    LoginDetails loginDetails = loginDetailsDao.getByUsername(username);
    String clientId = loginDetails.getClient().getId();
    Client clientInDB = clientDao.getClient(clientId);
    if (clientInDB == null) {
      throw new UsernameNotFoundException("User not Found");
    }
    return new AppUser(clientInDB, loginDetails);
  }
}