package psoft.proj.backend.ajude.campaigns.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.entities.Comment;
import psoft.proj.backend.ajude.campaigns.repositorys.CommentsRepository;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    private JwtService jwtService;
    private CampaignsService campaignsService;
    private CommentsRepository commentsDAO;

    public CommentsService (JwtService jwtService, CampaignsService campaignsService,
                            CommentsRepository commentsDAO) {
        super();
        this.jwtService = jwtService;
        this.campaignsService = campaignsService;
        this.commentsDAO = commentsDAO;
    }

    public Comment addComment(String header, String url, Comment comment) throws ServletException {
        if (!jwtService.userExists(header))
            throw new ServletException("User not found.");
        if (!campaignsService.contaisUrl(url))
            throw new ServletException("Campaign not found.");

        comment.setOwner(jwtService.getTokenSubject(header));
        comment.setCampaign(url);
        return (Comment) commentsDAO.save(comment);
    }

    public List<Comment> getCampaignsComments(String url) throws ServletException {
        return campaignsService.getCampaign(url).getComments();
    }
}
