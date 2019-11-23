package psoft.proj.backend.ajude.campaigns.entities;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
public class Comment {

    private String id;
    // owner email
    private String owner;
    // campaign url
    private String campaign;

	private boolean isAnswer;
    private List<Comment> answers;
    private String text;

    public Comment () {
        super();
    }

	public Comment (String text) {
		super();
		this.text = text;
		this.answers = new LinkedList<>();
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

	public boolean getIsAnswer() {
		return this.isAnswer;
	}

	public void instanciationAnswers(){
		this.answers = new ArrayList<>();
	}

	public void setIsAnswer() {
		this.isAnswer = true;
	}

	public List<Comment> getAnswers() {
		return this.answers;
	}

	public void setAnswer(Comment comment) {
		this.answers.add(comment);
	}
}
