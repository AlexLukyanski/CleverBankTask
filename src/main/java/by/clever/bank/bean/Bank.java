package by.clever.bank.bean;

import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Bank implements Serializable {

    @Serial
    private static final long serialVersionUID = 3431382944925312731L;
    private int id;
    private String name;
    private List<User> userList;
}
