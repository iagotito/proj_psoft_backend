package psoft.proj.backend.ajude.campaigns.entities;

import lombok.Data;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private double donations;
    @OneToMany(targetEntity = Comment.class, mappedBy = "campaign",
            cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Campaign() {
        super();
    }

    public Campaign(String owner, String name, String url, String description,
                    String deadline, double goal) {
        super();
        this.owner = owner;
        this.name = name;
        this.url = url;
        this.description = description;
        this.deadline = deadline;
        this.goal = goal;
        donations = 0;
        comments = new LinkedList();
    }

    // TODO: usar isso para alterar o status
    public void checkStatus() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        Date currentDate = new Date();
        Date deadline = sdf.parse(this.deadline);

        // Se a data atual está antes da data final, não altera o status
        if (currentDate.compareTo(deadline) > 0)
            return;

        if (donations >= goal)
            setStatus("concluída");
        else
            setStatus("vencida");
    }
}
