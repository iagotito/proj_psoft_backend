package psoft.proj.backend.ajude.campaigns.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;

@Data
@Document(collection = "Comment")
public class Comment {

    @Id
    private String id;
    // owner email
    private String owner;
    // campaign url
    private String campaign;
    private boolean isResposta;
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

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCampaign() {
		return campaign;
	}

	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Comment (String text) {
        super();
        this.text = text;
        //this.answers = new LinkedList<>();
    }

}
