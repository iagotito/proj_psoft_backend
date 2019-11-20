package psoft.proj.backend.ajude.users.entities;

import lombok.Data;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class User{
    @Id
    private String email;
    private String fname;
    private String lname;
    private String password;
    private String credit_card;
    @OneToMany(targetEntity = Campaign.class, mappedBy = "owner",
            cascade = CascadeType.ALL)
    private List campaigns;

    private User () { super(); }

    private User (String email, String fname, String lname, String password, String credit_card) {
        super();
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
        this.credit_card = credit_card;
        campaigns = new LinkedList<>();
    }
}
