package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.model.DutyAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DutyAccountDaoImpl implements DutyAccountDao
{
    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public DutyAccountDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<DutyAccount> rowMapper = (rs, rowNum) -> {
        DutyAccount dutyAccount = new DutyAccount();
        dutyAccount.setId(rs.getLong("id"));
        dutyAccount.setClientId(rs.getString("client_id"));
        dutyAccount.setCurrency(DutyAccount.CurrencyType.valueOf(rs.getString("currency").toUpperCase()));
        dutyAccount.setDuty(rs.getBigDecimal("duty"));
        dutyAccount.setStartDate(rs.getTimestamp("start_date").toLocalDateTime());

        return dutyAccount;
    };

    public List<DutyAccount> getAll(){
        String sql =
                "SELECT           "
               +"id,              "
               +"client_id,       "
               +"currency,        "
               +"duty,            "
               +"start_date       "
               +"FROM DUTY_ACCOUNT";

        return jdbcTemplate.query(sql,rowMapper);

    }

    @Override
    public void updateDutyAccount(DutyAccount dutyAccount) {
        //@formatter:off
        String sql =
                "UPDATE duty_account "
              + "SET duty = :duty    "
              + "WHERE id = :id      ";
        //@formatter:on

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("duty", dutyAccount.getDuty());
        parameterSource.addValue("id", dutyAccount.getId());

        jdbcTemplate.update(sql, parameterSource);
    }

    @Override
    public DutyAccount getDutyAccountById(Long id) {
        //@formatter:off
        String sql =
                "SELECT id, client_id,  currency, duty, start_date "
              + "FROM DUTY_ACCOUNT                                 "
              + "WHERE id = :id                                    ";
        //@formatter:on

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);

        try {
            return jdbcTemplate.queryForObject(sql, parameterSource, rowMapper);
        } catch (DataAccessException e) {
            return new DutyAccount();
        }
    }

    @Override
    public DutyAccount getDutyAccountByClientId(String clientId) {
        //@formatter:off
        String sql =
                "SELECT id, client_id, currency, duty, start_date "
              + "FROM DUTY_ACCOUNT                                "
              + "WHERE client_id = :clientId                      ";
        //@formatter:on

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("clientId", clientId);

        try {
            return jdbcTemplate.queryForObject(sql, parameterSource, rowMapper);
        } catch (DataAccessException e) {
            return new DutyAccount();
        }
    }

    @Override
    public int deleteDutyAccountById(Long id) {
        //@formatter:off
        String sql =
                "DELETE FROM DUTY_ACCOUNT "
              + "WHERE id = :id           ";
        //@formatter:on

        MapSqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("id", id);

        return jdbcTemplate.update(sql, paramSource);
    }
}
