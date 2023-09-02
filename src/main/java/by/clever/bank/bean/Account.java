package by.clever.bank.bean;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Account entity bean
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = -1906924248287665863L;
    private int id;
    private String number;
    private BigDecimal balance;
    private Bank bank;
    private List<Transaction> transactionList;
}
