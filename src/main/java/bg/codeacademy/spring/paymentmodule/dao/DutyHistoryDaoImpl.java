package bg.codeacademy.spring.paymentmodule.dao;

import bg.codeacademy.spring.paymentmodule.exception.ElementAlreadyExistsException;
import bg.codeacademy.spring.paymentmodule.model.DutyHistory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DutyHistoryDaoImpl implements DutyHistoryDao
{
  private final NamedParameterJdbcTemplate jdbc;
  DutyAccountDaoImpl accountDao;

  public DutyHistoryDaoImpl(NamedParameterJdbcTemplate jdbc, DutyAccountDaoImpl accountDao)
  {
    this.jdbc = jdbc;
    this.accountDao = accountDao;
  }

  public int saveRecord(DutyHistory record){
    String sql =
        "INSERT INTO Duty_History (duty_account_id, payment, time, transaction_reference) " +
        "VALUES(:account, :payment, :time, :transactionReference)                         ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("account", record.getAccount().getId())
        .addValue("payment", record.getPayment())
        .addValue("time", Timestamp.valueOf(record.getTime()))
        .addValue("transactionReference", record.getTransactionReference());

    try {
      return jdbc.update(sql, paramSource);
    }
    catch (DataAccessException e) {
      throw new ElementAlreadyExistsException("Saving history record failed!");
    }
  }

  public DutyHistory getByRecordId(Long recordId){
    String sql =
        "SELECT RECORD_ID, DUTY_ACCOUNT_ID, PAYMENT, TIME, TRANSACTION_REFERENCE " +
        "FROM duty_history                                                       " +
        "WHERE record_id = :recordId                                             ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("recordId", recordId);

    try {
      return jdbc.queryForObject(sql, paramSource, (rs, rowNum) -> {
        DutyHistory res = new DutyHistory();
        res.setRecordId(rs.getLong("RECORD_ID"));
        res.setAccount(accountDao.getDutyAccountById(rs.getLong("DUTY_ACCOUNT_ID")));
        res.setPayment(rs.getBigDecimal("PAYMENT"));
        res.setTime(rs.getTimestamp("TIME").toLocalDateTime());
        res.setTransactionReference(rs.getString("TRANSACTION_REFERENCE"));

        return res;
      });
    }
    catch (DataAccessException e) {
      return null;
    }
  }

  public List<DutyHistory> getAllDutyHistoryByAccountId(Long id){
    String sql =
        "SELECT RECORD_ID, DUTY_ACCOUNT_ID, PAYMENT, TIME, TRANSACTION_REFERENCE " +
        "FROM duty_history                                                       " +
        "WHERE duty_account_id = :id                                             ";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("id", id);

    try {
      return jdbc.query(sql, paramSource, rs -> {
        List<DutyHistory> res = new ArrayList<>();

        while (rs.next()){
          DutyHistory rec = new DutyHistory();
          rec.setRecordId(rs.getLong("RECORD_ID"));
          rec.setAccount(accountDao.getDutyAccountById(rs.getLong("DUTY_ACCOUNT_ID")));
          rec.setPayment(rs.getBigDecimal("PAYMENT"));
          rec.setTime(rs.getTimestamp("TIME").toLocalDateTime());
          rec.setTransactionReference(rs.getString("TRANSACTION_REFERENCE"));

          res.add(rec);
        }
        return res;
      });
    } catch (DataAccessException e) {
      e.printStackTrace();
      throw new ElementAlreadyExistsException("No records found for duty account with id " + id);
    }
  }

  public int deleteByRecordId(Long recordId){
    String sql = "DELETE FROM Duty_History WHERE record_id = :recordId";

    MapSqlParameterSource paramSource = new MapSqlParameterSource()
        .addValue("recordId", recordId);

    try {
      return jdbc.update(sql, paramSource);
    }
    catch (DataAccessException e) {
      return 0;
    }
  }

}
