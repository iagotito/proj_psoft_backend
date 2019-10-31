package psoft.proj.backend.ajude.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.services.UsersService;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {

    private UsersService usersService;

    public UsersController (UsersService usersService) {
        super ();
        this.usersService = usersService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> postUser (@RequestBody User user) throws ServerException {
        try {
            return new ResponseEntity<>(usersService.postUser(user), HttpStatus.CREATED);
        } catch (ServerException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers () {
        return new ResponseEntity<>(usersService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<User> getUser (@PathVariable String email) {
        Optional<User> user = usersService.getUser(email);
        if (user.isPresent())
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
