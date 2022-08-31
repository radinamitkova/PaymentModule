package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.Token;

public interface TokenDao
{
  int saveToken(Token token);

  Token getTokenById(int tokenId);

  Token getTokenByTokenBody(String tokenBody);
}
