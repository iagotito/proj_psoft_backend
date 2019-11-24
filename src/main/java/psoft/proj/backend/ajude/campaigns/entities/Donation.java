package psoft.proj.backend.ajude.campaigns.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Donation")
public class Donation {

    @Id
    private String id;
    private double amount;
    // campaign url
    private String campaign;
    // owner email
    private String owner;
    private String date;

    public Donation () {
        super();
    }

    public Donation (String url, String email, double amount, String date) {
        super();
        this.amount = amount;
        this.campaign = url;
        this.owner = email;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String url) {
        this.campaign = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
