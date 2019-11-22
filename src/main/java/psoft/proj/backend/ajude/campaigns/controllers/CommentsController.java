package psoft.proj.backend.ajude.campaigns.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.auxiliaryEntities.ExceptionResponse;
import psoft.proj.backend.ajude.campaigns.entities.Comment;
import psoft.proj.backend.ajude.campaigns.services.CommentsService;

import java.util.List;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/campaigns/{url}/comments")
public class CommentsController {

    private CommentsService commentsService;

    public CommentsController (CommentsService commentsService) {
        super();
        this.commentsService = commentsService;
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> commentCampaign (@RequestHeader("Authorization") String header, @PathVariable String url,
                                                    @RequestBody Comment comment) {
        try {
            return new ResponseEntity<Comment>(commentsService.addComment(header, url, comment),
                    HttpStatus.CREATED);
        } catch (ServletException e) {
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
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<?> getCampaignComments (@PathVariable String url) {
        try {
            return new ResponseEntity<List<Comment>>(commentsService.getCampaignsComments(url), HttpStatus.OK);
        } catch (ServletException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
