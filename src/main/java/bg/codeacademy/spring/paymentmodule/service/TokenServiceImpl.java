package bg.codeacademy.spring.paymentmodule.service;

import bg.codeacademy.spring.paymentmodule.dao.TokenDao;
import bg.codeacademy.spring.paymentmodule.exception.ExpiredTokenException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.Token;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TokenServiceImpl implements TokenService
{
  private final TokenDao      tokenDao;
  private final ClientService clientService;

  public TokenServiceImpl(TokenDao tokenDao, ClientService clientService)
  {
    this.tokenDao = tokenDao;
    this.clientService = clientService;
  }

  @Override
  public Token generateToken(Token.TokenType tokenType, String clientId)
  {
    String tokenBody = generateRandomTokenBody();

    Client client = clientService.getClientByID(clientId);
    Token token = new Token(tokenBody, tokenType, client);
    tokenDao.saveToken(token);
    return token;
  }

  @Override
  public String getClientIdByTokenBody(String tokenBody)
  {
    Token token = tokenDao.getTokenByTokenBody(tokenBody);

    //verify token is still valid
    if (LocalDateTime.now().isAfter(token.getActiveTo())) {
      throw new ExpiredTokenException("Token has expired!");
    }
    return token.getClient().getId();
  }

  private String generateRandomTokenBody()
  {
    String allowedCharacters = "abcdefghijklmnopqrstuvwxyz0123456789";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    int tokenLength = 20;
    int randomIndex;

    for (int i = 0; i < tokenLength; i++) {
      randomIndex = random.nextInt(allowedCharacters.length());
      sb.append(allowedCharacters.charAt(randomIndex));
    }
    return sb.toString();
  }
}
