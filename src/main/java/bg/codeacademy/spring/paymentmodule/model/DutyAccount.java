package bg.codeacademy.spring.paymentmodule.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class DutyAccount {

    private Long id;

    @NonNull
    private String clientId;

    @NonNull
    private CurrencyType currency;
    private BigDecimal duty;
    private LocalDateTime startDate;

    public enum CurrencyType {
        BGN,
        EUR,
        USD,
        GBP
    }

}

