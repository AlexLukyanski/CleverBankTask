package by.clever.bank.bean;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -2799171450284921710L;
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String email;
    private List<Account> accountList;
}
