package psoft.proj.backend.ajude.campaigns.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue
    private int id;
    // owner email
    private String owner;
    // campaign url
    private String campaign;
    private String text;
/*
    @OneToMany(targetEntity = Comment.class, mappedBy = "id",
            cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> answers;

 */
    public Comment () {
        super();
    }

    public Comment (String text) {
        super();
        this.text = text;
        //this.answers = new LinkedList<>();
    }

}
