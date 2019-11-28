package psoft.proj.backend.ajude.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.auxiliaryEntities.ExceptionResponse;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.services.CampaignsService;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.services.JwtService;
import psoft.proj.backend.ajude.users.services.UsersService;

import javax.servlet.ServletException;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;
    private JwtService jwtService;
    private CampaignsService campaignsService;

    public UsersController (UsersService usersService, JwtService jwtService, CampaignsService campaignsService) {
        super ();
        this.usersService = usersService;
        this.jwtService = jwtService;
        this.campaignsService = campaignsService;
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> createUser (@RequestBody User user) {
        try {
            return new ResponseEntity<User>(usersService.createUser(user), HttpStatus.CREATED);
        } catch (ServerException e) {
            if(e.getMessage().equals("E-mail already registered.")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.FORBIDDEN);
            } else{
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            }

        }
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<User>> getUsers () {
        return new ResponseEntity<List<User>>(usersService.getUsers(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/auth/get-user")
    public ResponseEntity<?> getUserByHeader (@RequestHeader("Authorization") String header) {
        try {
            return new ResponseEntity<User>(jwtService.getUserByHeader(header), HttpStatus.OK);
        } catch (ServletException e) {
            if (e.getMessage().equals("Token inexistente ou mal formatado!"))
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            else
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @GetMapping("/{email}")
    public ResponseEntity<?> getUser (@PathVariable String email) {
        try {
            return new ResponseEntity<User>(usersService.getUser(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping(path = {"{email}/campaigns",
            "{email}/campaigns/{substring}"})
    public ResponseEntity<?> getUserCampaignsDonated (@PathVariable String email, @PathVariable("substring") Optional<String> substring) {
        try {
            if(substring.isPresent()){
                return new ResponseEntity<List<Campaign>>(usersService.getUserCampaigns(email, substring.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Campaign>>(usersService.getUserCampaigns(email, ""), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping(path = {"{email}/campaignsDonated",
            "{email}/campaignsDonated/{substring}"})
    public ResponseEntity<?> getUserCampaigns (@PathVariable String email, @PathVariable("substring") Optional<String> substring) {
        try {
            if(substring.isPresent()){
                return new ResponseEntity<List<Campaign>>(usersService.getUserCampaignsDonated(email, substring.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Campaign>>(usersService.getUserCampaignsDonated(email, ""), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
