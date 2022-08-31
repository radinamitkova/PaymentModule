package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.Client;
import bg.codeacademy.spring.paymentmodule.model.LoginDetails;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDetailsDaoImpl implements LoginDetailsDao
{
  private final NamedParameterJdbcTemplate jdbc;
  private final RowMapper<LoginDetails> rowMapper;

  public LoginDetailsDaoImpl(NamedParameterJdbcTemplate jdbc, ClientDao clientDao)
  {
    this.jdbc = jdbc;

    rowMapper = (rs, rowNum) -> {
      Client client = clientDao.getClient(rs.getString("client_id"));

      LoginDetails l = new LoginDetails();
      l.setLoginId(rs.getLong("LOGIN_ID"));
      l.setClient(client);
      l.setUsername(rs.getString("USERNAME"));
      l.setPassword(rs.getString("PASSWORD"));
      l.setActive(rs.getBoolean("IS_ACTIVE"));

      return l;
    };
  }

  public int saveLoginDetails(LoginDetails l){
    String sql =
        "INSERT INTO Login_Details (client_id, username, password, is_active) " +
        "VALUES(:clientId, :username, :password, :isActive)                  ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("clientId", l.getClient().getId())
        .addValue("username", l.getUsername())
        .addValue("password", l.getPassword())
        .addValue("isActive", l.isActive());

    try {
      return jdbc.update(sql, paramSource);
    }
    catch (DataAccessException e) {
      e.printStackTrace();
      return 0;
    }
  }

  public int updateLoginDetails(LoginDetails loginDetails)
  {
    String sql =
        "UPDATE Login_Details        " +
        "SET client_id = :clientId, " +
        "    username  = :username,  " +
        "    password  = :password,  " +
        "    is_active = :activated  " +
        "WHERE login_id = :loginId  ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("loginId", loginDetails.getLoginId())
        .addValue("clientId", loginDetails.getClient().getId())
        .addValue("username", loginDetails.getUsername())
        .addValue("password", loginDetails.getPassword())
        .addValue("activated", loginDetails.isActive());

    try {
      return jdbc.update(sql, paramSource);
    }
    catch (DataAccessException e) {
      return 0;
    }
  }

  public LoginDetails getByUsername(String username) {
    String sql =
        "SELECT login_id, client_id, username, password, is_active " +
        "FROM Login_Details                                        " +
        "WHERE username = :username                                ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("username", username);

    try {
      return jdbc.queryForObject(sql, paramSource, rowMapper);
    } catch (DataAccessException e) {
      return  null;
    }
  }

  public LoginDetails getByUserID(String clientID){
    String sql =
        "SELECT login_id, client_id, username, password, is_active " +
        "FROM Login_Details                                        " +
        "WHERE client_id = :id                                     ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("id", clientID);

    try {
      return jdbc.queryForObject(sql, paramSource, rowMapper);
    }
    catch (DataAccessException e) {
      return null;
    }
  }

  public LoginDetails getByUserEmail(String email){
    String sql =
        "SELECT login_id, client_id, username, password, is_active " +
        "FROM Login_Details                                        " +
        "WHERE client_id =                                         " +
        "             (SELECT id FROM Clients WHERE email = :email)";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("email", email);

        try {
            return jdbc.queryForObject(sql, paramSource, rowMapper);
        } catch (DataAccessException e) {
          return null;
        }
    }

  public LoginDetails getByUserPhone(String phone){
    String sql =
        "SELECT login_id, client_id, username, password, is_active " +
        "FROM Login_Details                                        " +
        "WHERE client_id =                                         " +
        "      (SELECT id FROM Clients WHERE phone_number = :phone)";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("phone", phone);

        try {
            return jdbc.queryForObject(sql, paramSource, rowMapper);
        } catch (DataAccessException e) {
          return null;
        }
    }

  public int deleteByLoginId(Long loginID) {
    String sql = "DELETE FROM Login_Details WHERE login_id = :id";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("id", loginID);

    try {
      return jdbc.update(sql, paramSource);
    }
    catch (DataAccessException e) {
      return 0;
    }
  }

  public int deleteByUserId(Long clientID) {
    String sql = "DELETE FROM Login_Details WHERE client_id = :id";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("id", clientID);

    try {
      return jdbc.update(sql, paramSource);
    }
    catch (DataAccessException e) {
      return 0;
    }
  }

}
