package bg.codeacademy.spring.paymentmodule.model;

import lombok.Data;

@Data
public class LoginDetails
{
  private Long loginId;

  private Client client;

  private String username;

  private String password;

  private boolean isActive;
}
