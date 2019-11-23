package psoft.proj.backend.ajude.campaigns.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Document(collection = "Campaign")
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

    private List<Comment> comments;
    private List<String> likes;

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
        this.donations = 0;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
    }

    public double getDonations() {
        return donations;
    }

    public void setDonations(double donations) {
        this.donations = donations;
    }

    public List<Comment> getComments() {
        return this.comments;
    }

    public void instanciationComments(){
        this.comments = new LinkedList<>();
    }

    public void instanciationLikes(){
        this.likes = new ArrayList<>();
    }

    public Comment getCommentById(String id){
        for(int i = 0; i < this.comments.size(); i++){
            if(this.comments.get(i).getId().equals(id)){
                return this.comments.get(i);
            }
        }
        return null;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(String email) {
        this.likes.add(email);
    }

    public void removeLike(String email) {
        this.likes.remove(email);
    }
}
