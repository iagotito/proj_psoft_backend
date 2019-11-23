package psoft.proj.backend.ajude.users.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;

import java.util.LinkedList;
import java.util.List;

@Data
@Document(collection = "User")
public class User{
    @Id
    private String email;
    private String fname;
    private String lname;
    private String password;
    private String credit_card;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    private User () { super(); }

    private User (String email, String fname, String lname, String password, String credit_card) {
        super();
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.credit_card = credit_card;
    }
}
