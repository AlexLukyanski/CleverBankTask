package by.clever.bank.bean;

import by.clever.bank.bean.constant.TransactionType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Transaction implements Serializable {

    @Serial
    private static final long serialVersionUID = -7726117802367162918L;
    private long id;
    private TransactionType type;
    private LocalDateTime dateTime;
    private BigDecimal amount;
}
