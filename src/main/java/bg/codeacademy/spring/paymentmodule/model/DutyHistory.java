package bg.codeacademy.spring.paymentmodule.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DutyHistory
{
  private Long recordId;

  @NotNull
  private DutyAccount account;

  @NotNull
  private BigDecimal payment;

  @NotNull
  private LocalDateTime time;

  @NotNull
  private String transactionReference;
}
