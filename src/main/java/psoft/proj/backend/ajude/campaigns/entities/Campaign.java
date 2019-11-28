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
    private double donated;

    private String date;

    private List<String> donationsIds;
    private List<Comment> comments;
    private List<String> likes;

    public Campaign() {
        super();
    }

    public Campaign(String owner, String name, String url, String description,
                    String deadline, double goal, String date) {
        super();
        this.owner = owner;
        this.name = name;
        this.url = url;
        this.description = description;
        this.deadline = deadline;
        this.goal = goal;
        this.donated = 0;
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
        this.date = date;
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
        return this.donated;
    }

    public void setDonations(double donations) {
        this.donated += donations;
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

    public void instanciationDonationsIds(){
        this.donationsIds = new ArrayList<>();
    }

    public Comment getCommentById(String id){
        for(int i = 0; i < this.comments.size(); i++){
            // Caso o comentario possua respostas (ou seja, caso sua lista de respostas não seja vazia)
            if(!this.comments.get(i).getAnswers().isEmpty()) {
                // cria-se um laço até o tamanho da lista de respostas
                for (int j = 0; j < this.comments.get(i).getAnswers().size(); j++) {
                    // acessa-se cada elemento da lista de respostas daquele comentario, comparando o id
                    if (this.comments.get(i).getAnswers().get(j).getId().equals(id)) {
                        return this.comments.get(i).getAnswers().get(j);
                    }
                }
            }
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

    public List<String> getDonationsIds() {
        return donationsIds;
    }

    public void setDonationsIds(String donationsIds) {
        this.donationsIds.add(donationsIds);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
