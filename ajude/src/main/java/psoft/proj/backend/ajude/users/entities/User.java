package psoft.proj.backend.ajude.users.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User{
    @Id
    private String email;
    private String fname;
    private String lname;
    private String password;
    private String creditCard;

    private User () { super(); }

    private User (String email, String fname, String lname, String password, String creditCard) {
        super();
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.creditCard = creditCard;
    }


}
