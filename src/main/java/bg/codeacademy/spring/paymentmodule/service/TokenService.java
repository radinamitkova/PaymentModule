package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.model.Token;

public interface TokenService
{
  Token generateToken(Token.TokenType tokenType, String clientId);

  String getClientIdByTokenBody(String tokenBody);
}
