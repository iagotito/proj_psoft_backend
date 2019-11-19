package psoft.proj.backend.ajude.campaigns.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Campaign {
    private String owner;
    private String name;
    @Id
    private String url;
    private String description;
    private String deadline;
    private String status;
    private double goal;

    public Campaign () {
        super ();
    }

    public Campaign (String owner, String name, String url, String description,
                     String deadline, String status, double goal) {
        super ();
        this.owner = owner;
        this.name = name;
        this.url = url;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
        this.goal = goal;
    }
}
