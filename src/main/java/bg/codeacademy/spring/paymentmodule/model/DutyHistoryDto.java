package bg.codeacademy.spring.paymentmodule.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DutyHistoryDto
{
  private Long recordId;

  private BigDecimal payment;

  private LocalDateTime time;

  private String transactionReference;

  public static DutyHistoryDto of(DutyHistory model){
    DutyHistoryDto dto = new DutyHistoryDto();
    dto.setRecordId(model.getRecordId());
    dto.setPayment(model.getPayment());
    dto.setTime(model.getTime());
    dto.setTransactionReference(model.getTransactionReference());
    return dto;
  }
}
