package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.exception.NoDataException;
import bg.codeacademy.spring.paymentmodule.model.Client;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ClientDaoImpl implements ClientDao
{
  private String sql;

  private final NamedParameterJdbcTemplate jdbcTemplate;

  public ClientDaoImpl(NamedParameterJdbcTemplate jdbcTemplate)
  {
    this.jdbcTemplate = jdbcTemplate;
  }

  RowMapper<Client> rowMapper = (rs, rowNum) -> {
    Client client = new Client();
    client.setId(rs.getString("id"));
    client.setName(rs.getString("name"));
    client.setEmail(rs.getString("email"));
    client.setPhoneNumber(rs.getString("phone_number"));
    return client;
  };

  @Override
  public List<Client> getAll()
  {
    sql =
        "SELECT           "
       +"    id,          "
       +"    name,        "
       +"    email,       "
       +"    phone_number "
       +"FROM             "
       +"    clients      ";

    return jdbcTemplate.query(sql, rowMapper);
  }

  public List<Client> getUnregisteredClients()
  {
    sql =
        "SELECT                                              "
       +"    id,                                             "
       +"    name,                                           "
       +"    email,                                          "
       +"    phone_number                                    "
       +"FROM                                                "
       +"    clients       c                                 "
       +"    LEFT JOIN login_details l ON c.id = l.client_id "
       +"WHERE                                               "
       +"    l.client_id IS NULL                             ";

    return jdbcTemplate.query(sql, rowMapper);
  }

  public List<Client> getRegisteredClients()
  {
    sql =
        "SELECT                                        "
       +"    id,                                       "
       +"    name,                                     "
       +"    email,                                    "
       +"    phone_number                              "
       +"FROM                                          "
       +"    clients c                                 "
       +"    JOIN login_details l ON c.id = l.client_id";

    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public void createClient(Client client)
  {
    sql =
        "INSERT INTO clients ("
       +"    id,              "
       +"    name,            "
       +"    email,           "
       +"    phone_number     "
       +") VALUES (           "
       +"    :id,             "
       +"    :name,           "
       +"    :email,          "
       +"    :phone_number)   ";

    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    parameterSource.addValue("id", client.getId())
                   .addValue("name", client.getName())
                   .addValue("email", client.getEmail())
                   .addValue("phone_number", client.getPhoneNumber());

    jdbcTemplate.update(sql, parameterSource);
  }

  @Override
  public Client getClient(String id)
  {
    sql =
        "SELECT           "
       +"    id,          "
       +"    name,        "
       +"    email,       "
       +"    phone_number "
       +"FROM             "
       +"    clients      "
       +"WHERE            "
       +"    id = :id     ";

    Client client;
    MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", id);
    try {
      client = jdbcTemplate.queryForObject(sql, parameterSource, rowMapper);
      return client;

    }
    catch (EmptyResultDataAccessException ex) {
      throw new NoDataException("Client with id " + id + " not found!");
    }
  }

  @Override
  public void updateClient(Client client)
  {
    sql =
        "UPDATE clients                   "
       +"SET                              "
       +"    name = :name,                "
       +"    email = :email,              "
       +"    phone_number = :phone_number "
       +"WHERE                            "
       +"    id = :id                     ";


    MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    parameterSource.addValue("id", client.getId())
                   .addValue("name", client.getName())
                   .addValue("email", client.getEmail())
                   .addValue("phone_number", client.getPhoneNumber());

    jdbcTemplate.update(sql, parameterSource);
  }

  @Override
  public void deleteClient(String id)
  {
    sql =
        "DELETE FROM clients "
       +"WHERE               "
       +"    id = :id        ";

    MapSqlParameterSource parameterSource = new MapSqlParameterSource("id", id);

    jdbcTemplate.update(sql, parameterSource);
  }

  public Client getClientByEmail(String email)
  {
    sql =
        "SELECT            "
       +"    id,           "
       +"    name,         "
       +"    email,        "
       +"    phone_number  "
       +"FROM              "
       +"    clients       "
       +"WHERE             "
       +"    email = :email";

    Client client;
    MapSqlParameterSource parameterSource = new MapSqlParameterSource("email", email);
    try {
      client = jdbcTemplate.queryForObject(sql, parameterSource, rowMapper);
      return client;
    }
    catch (EmptyResultDataAccessException ex) {
      throw new NoDataException("Client with email " + email + " not found!");
    }
  }

  public List<Client> getClientsForFirstEmail()
  {
    sql =
        "SELECT                                                      "
        +"    x.id,                                                  "
        +"    name,                                                  "
        +"    email,                                                 "
        +"    phone_number                                           "
        +"FROM                                                       "
        +"    (                                                      "
        +"        SELECT                                             "
        +"            id,                                            "
        +"            name,                                          "
        +"            email,                                         "
        +"            phone_number                                   "
        +"        FROM                                               "
        +"            clients       c                                "
        +"            LEFT JOIN login_details l ON c.id = l.client_id"
        +"        WHERE                                              "
        +"            l.client_id IS NULL                            "
        +"    ) x                                                    "
        +"    JOIN duty_account b ON x.id = b.client_id              "
        +"WHERE                                                      "
        +"    b.start_date <= SYSDATE                                 ";

    try{
      List<Client> clients = jdbcTemplate.query(sql, rowMapper);
      return clients;
    }

    catch (DataAccessException ex){
      return null;
    }
  }
}
