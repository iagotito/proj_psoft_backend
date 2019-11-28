package psoft.proj.backend.ajude.campaigns.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.auxiliaryEntities.ExceptionResponse;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.entities.Comment;
import psoft.proj.backend.ajude.campaigns.services.CommentsService;
import psoft.proj.backend.ajude.users.services.JwtService;

import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/campaigns/{url}/comments")
public class CommentsController {

    private CommentsService commentsService;
    private JwtService jwtService;

    public CommentsController (CommentsService commentsService, JwtService jwtService) {
        super();
        this.commentsService = commentsService;
        this.jwtService = jwtService;
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> commentCampaign (@RequestHeader("Authorization") String header, @PathVariable String url,
                                                    @RequestBody Comment comment) {
        try {
            if (!jwtService.userExists(header))
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("Header does not correspond to any user."),
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<Comment>(commentsService.addComment(header, url, comment),
                    HttpStatus.CREATED);
        } catch (ServletException e) {
            if(e.getMessage().equals("Token inexistente ou mal formatado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Token invalido ou expirado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @PostMapping("/{id}/answers")
    public ResponseEntity<?> commentComment (@RequestHeader("Authorization") String header, @PathVariable String url,
                                             @PathVariable String id, @RequestBody Comment comment) {
        try {
            return new ResponseEntity<Comment>(commentsService.addAnswer(header, url, id, comment),
                    HttpStatus.CREATED);
        } catch (ServletException e) {
            if(e.getMessage().equals("User not found.") || e.getMessage().equals("Campaign not found.")
                    || e.getMessage().equals("Comment not found")) {
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.FORBIDDEN);
            }
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment (@RequestHeader("Authorization") String header, @PathVariable String url,
                                             @PathVariable String id) {
        try {
            if (!jwtService.userExists(header))
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("Header does not correspond to any user."),
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<Campaign>(commentsService.deleteComment(header, url, id),
                    HttpStatus.OK);
        } catch (ServletException e) {
            if(e.getMessage().equals("User not found.") || e.getMessage().equals("Campaign not found.")
                    || e.getMessage().equals("Comment not found")) {
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.NOT_FOUND);
            } else if(e.getMessage().equals("Token inexistente ou mal formatado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Token invalido ou expirado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<?> getCampaignComments (@PathVariable String url) {
        try {
            return new ResponseEntity<List<Comment>>(commentsService.getCampaignsComments(url), HttpStatus.OK);
        } catch (ServletException | ParseException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/{id}/answers")
    public ResponseEntity<?> getCommentsAnswers (@RequestHeader("Authorization") String header, @PathVariable String url, @PathVariable String id) {
        try {
            if (!jwtService.userExists(header))
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("Header does not correspond to any user."),
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<List<Comment>>(commentsService.getCommentsAnswers(url, id), HttpStatus.OK);
        } catch (ServletException | ParseException e) {
            if(e.getMessage().equals("Token inexistente ou mal formatado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Token invalido ou expirado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
