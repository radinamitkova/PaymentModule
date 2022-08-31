package bg.codeacademy.spring.paymentmodule.security;

import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.LoginDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AppUser implements UserDetails
{
  Client client;
  LoginDetails loginDetails;

  public AppUser(Client client, LoginDetails loginDetails)
  {
    this.client = client;
    this.loginDetails = loginDetails;
  }

  public AppUser()
  {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return AuthorityUtils.createAuthorityList("ROLE_user");
  }

  @Override
  public String getPassword()
  {
    return this.loginDetails.getPassword();
  }

  @Override
  public String getUsername()
  {
    return this.loginDetails.getUsername();
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }

  public Client getClient()
  {
    return client;
  }

  public void setClient(Client client)
  {
    this.client = client;
  }

  public LoginDetails getLoginDetails()
  {
    return loginDetails;
  }

  public void setLoginDetails(LoginDetails loginDetails)
  {
    this.loginDetails = loginDetails;
  }
}