package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.exception.ElementAlreadyExistsException;
import bg.codeacademy.spring.paymentmodule.exception.NoDataException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.Token;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
public class TokenDaoImpl implements TokenDao
{
  private final NamedParameterJdbcTemplate namedJdbcTemplate;
  private final RowMapper<Token>           tokenRowMapper;

  public TokenDaoImpl(NamedParameterJdbcTemplate namedJdbcTemplate, ClientDao clientDao)
  {
    this.namedJdbcTemplate = namedJdbcTemplate;

    tokenRowMapper = (resultSet, rowNum) -> {
      Client client = clientDao.getClient(resultSet.getString("client_id"));
      Token token = new Token();
      token.setId(resultSet.getInt("id"));
      token.setTokenBody(resultSet.getString("token_body"));
      token.setTokenType(Token.TokenType.valueOf(resultSet.getString("token_type").toUpperCase()));
      token.setClient(client);
      token.setActiveFrom(resultSet.getTimestamp("active_from").toLocalDateTime());
      token.setActiveTo(resultSet.getTimestamp("active_to").toLocalDateTime());
      return token;
    };
  }

  @Override
  public Token getTokenById(int tokenId)
  {
    //@formatter:off
    String sql =
        "SELECT          "
       +"    id,         "
       +"    token_body, "
       +"    token_type, "
       +"    client_id,  "
       +"    active_from,"
       +"    active_to   "
       +"FROM            "
       +"    tokens      "
       +"WHERE id = :id  ";
    //@formatter:on

    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource("id", tokenId);

    try {
      return namedJdbcTemplate.queryForObject
          (sql, mapSqlParameterSource, tokenRowMapper);
    }
    catch (EmptyResultDataAccessException exception) {
      throw new NoDataException("Error in provided data");
    }
  }

  @Override
  public Token getTokenByTokenBody(String tokenBody)
  {
    //@formatter:off
    String sql =
        "SELECT                 "
       +"    id,                "
       +"    token_body,        "
       +"    token_type,        "
       +"    client_id,         "
       +"    active_from,       "
       +"    active_to          "
       +"FROM                   "
       +"    tokens             "
       +"WHERE                  "
       +"    token_body = :token";
    //@formatter:on

    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource("token", tokenBody);

    try {
      return namedJdbcTemplate.queryForObject
          (sql, mapSqlParameterSource, tokenRowMapper);
    }
    catch (EmptyResultDataAccessException exception) {
      throw new NoDataException("Error in provided data");
    }
  }

  @Override
  public int saveToken(Token token)
  {
    //@formatter:off
    String sql =
        "INSERT INTO tokens ("
       +"    token_body,     "
       +"    token_type,     "
       +"    client_id,      "
       +"    active_from,    "
       +"    active_to       "
       +") VALUES (          "
       +"    :tokenBody,     "
       +"    :tokenType,     "
       +"    :clientId,      "
       +"    :activeFrom,    "
       +"    :activeTo)      ";
    //@formatter:on

    MapSqlParameterSource mapSqlParameterSource =
        new MapSqlParameterSource("tokenBody", token.getTokenBody())
            .addValue("tokenType", token.getTokenType().toString())
            .addValue("clientId", token.getClient().getId())
            .addValue("activeFrom", Timestamp.valueOf(LocalDateTime.now()))
            .addValue("activeTo", Timestamp.valueOf(LocalDateTime.now().plusHours(1L)));

    try {
      return namedJdbcTemplate.update(sql, mapSqlParameterSource);
    }
    catch (DataAccessException exception) {
      throw new ElementAlreadyExistsException("Token already exists");
    }
  }
}
