package bg.codeacademy.spring.paymentmodule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Token
{
  private Integer       id;
  private String        tokenBody;
  private TokenType     tokenType;
  private LocalDateTime activeFrom;
  private LocalDateTime activeTo;
  private Client        client;

  public enum TokenType
  {
    REGISTRATION,
    ACTIVATION
  }

  public Token(String tokenBody, TokenType tokenType, Client client)
  {
    this.tokenBody = tokenBody;
    this.tokenType = tokenType;
    this.client = client;
  }
}
