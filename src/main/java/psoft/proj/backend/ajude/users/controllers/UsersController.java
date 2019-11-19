package psoft.proj.backend.ajude.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.campaigns.services.CampaignsService;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.services.JwtService;
import psoft.proj.backend.ajude.users.services.UsersService;

import javax.servlet.ServletException;
import java.rmi.ServerException;
import java.util.List;

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
    public ResponseEntity<User> postUser (@RequestBody User user) {
        try {
            return new ResponseEntity<>(usersService.postUser(user), HttpStatus.CREATED);
        } catch (ServerException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<User>> getUsers () {
        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.OK);
    }

    @CrossOrigin
    //todo: achar um nome melhor pra essa rota
    @GetMapping("/auth")
    public ResponseEntity<User> getUserByHeader (@RequestHeader("Authorization") String header) {
        try {
            return new ResponseEntity<>(jwtService.getUserByHeader(header), HttpStatus.OK);
        } catch (ServletException e) {
            if (e.toString().equals("Token inexistente ou mal formatado!"))
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @GetMapping("/{num}")
    public ResponseEntity<User> getUser (@PathVariable int num) {
        try {
            return new ResponseEntity<>(usersService.getUserByNum(num), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @DeleteMapping("")
    public ResponseEntity deleteUsers () {
        usersService.deleteUsers();
        return new ResponseEntity(HttpStatus.OK);
    }
}
