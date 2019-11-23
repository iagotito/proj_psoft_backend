package psoft.proj.backend.ajude.campaigns.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.entities.Comment;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    private JwtService jwtService;
    private CampaignsService campaignsService;
    private CampaignsRepository campaignsDAO;
    private int idCounter = 0;

    public CommentsService (JwtService jwtService, CampaignsService campaignsService, CampaignsRepository campaignsDAO) {
        super();
        this.jwtService = jwtService;
        this.campaignsService = campaignsService;
        this.campaignsDAO = campaignsDAO;
    }

    public Comment addComment(String header, String url, Comment comment) throws ServletException {
        if (!jwtService.userExists(header))
            throw new ServletException("User not found.");
        if (!campaignsService.contaisUrl(url))
            throw new ServletException("Campaign not found.");

        comment.setOwner(jwtService.getTokenSubject(header));
        comment.setCampaign(url);
        comment.setId(Integer.toString(this.idCounter++));
        comment.instanciationAnswers();

        Optional<Campaign> newCampaign = campaignsDAO.findById(url);

        if(newCampaign.isPresent()){
            newCampaign.get().getComments().add(comment);

            campaignsDAO.delete(campaignsDAO.findById(url).get());
            campaignsDAO.save(newCampaign.get());
        }

        return comment;
    }

    public Comment addAnswer(String header, String url, String id, Comment answer) throws ServletException {
        if (!jwtService.userExists(header))
            throw new ServletException("User not found.");
        if (!campaignsService.contaisUrl(url))
            throw new ServletException("Campaign not found.");

        answer.setOwner(jwtService.getTokenSubject(header));
        answer.setCampaign(url);
        answer.setId(Integer.toString(this.idCounter++));
        answer.setIsAnswer();
        answer.instanciationAnswers();

        Optional<Campaign> newCampaign = campaignsDAO.findById(url);

        if(newCampaign.isPresent()){
            List<Comment> comments = newCampaign.get().getComments();

            Comment comment = newCampaign.get().getCommentById(id);

            if(comment == null)
                throw new ServletException("Comment not found");
            if(comment.getIsAnswer())
                throw new ServletException("You can't comment on a answer");

            newCampaign.get().getCommentById(id).setAnswer(answer);
            campaignsDAO.delete(campaignsDAO.findById(url).get());
            campaignsDAO.save(newCampaign.get());
        }

        return answer;
    }

    public List<Comment> getCampaignsComments(String url) throws ServletException {

        List<Comment> notDeletedComments = new ArrayList<>();
        List<Comment> allComments = campaignsService.getCampaign(url).getComments();

        // Laço para percorrer toda a lista de comentários
        for(int i = 0; i < allComments.size(); i++){
            // Caso o comentário dessa lista não tenha sido deletado...
            if(!allComments.get(i).getWasDeleted()) {
                // Adiciona-se ele à lista de comentários não deletados
                notDeletedComments.add(allComments.get(i));
                // Caso esse comentário, que não foi apagado, possua respostas...
                if(!allComments.get(i).getAnswers().isEmpty()) {

                    List<Comment> answers = allComments.get(i).getAnswers();
                    // Laço para percorrer toda a lista de respostas
                    for(int j = 0; j < answers.size(); j++){
                        // Caso a resposta dentro do comentário não tenha sido deletada...
                        if(!answers.get(j).getWasDeleted()){
                            // Adiciona-se ela à lista de comentários não deletados
                            notDeletedComments.add(answers.get(j));
                        }
                    }
                }
            }
        }
        return notDeletedComments;
    }

    public Campaign deleteComment(String header, String url, String id) throws ServletException {
        if (!jwtService.userExists(header))
            throw new ServletException("User not found.");
        if (!campaignsService.contaisUrl(url))
            throw new ServletException("Campaign not found.");

        User user = jwtService.getUserByHeader(header);
        String email = user.getEmail();

        Optional<Campaign> newCampaign = campaignsDAO.findById(url);

        if(newCampaign.isPresent()){
            List<Comment> comments = newCampaign.get().getComments();

            Comment comment = newCampaign.get().getCommentById(id);


            if(comment == null)
                throw new ServletException("Comment not found");
            if(!email.equals(comment.getOwner()))
                throw new ServletException("You can't delete a comment that is not yours");

            newCampaign.get().getCommentById(id).setWasDeleted();
            campaignsDAO.delete(campaignsDAO.findById(url).get());
            campaignsDAO.save(newCampaign.get());
        }
        return newCampaign.get();
    }
}
