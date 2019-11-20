package psoft.proj.backend.ajude.users.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.services.JwtService;
import psoft.proj.backend.ajude.users.services.UsersService;

import javax.servlet.ServletException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private UsersService usersService;
    private JwtService jwtService;

    public LoginController(UsersService usersService, JwtService jwtService) {
        super();
        this.usersService = usersService;
        this.jwtService = jwtService;
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody User user) {

        try {
            // Recupera o usuario
            User authUser = usersService.getUser(user.getEmail());

            if (!authUser.getPassword().equals(user.getPassword()))
                return new ResponseEntity("{\"message\":\"Wrong password.\"}", HttpStatus.FORBIDDEN);

            String token = jwtService.generateToken(authUser.getEmail());
            return new ResponseEntity(new LoginResponse(token), HttpStatus.OK);
        } catch (ServletException e) {
            // Se tiver erro, é porque o usuário não existe
            return new ResponseEntity("{\"message\":\"" + e.getMessage() + "\"}",
                    HttpStatus.NOT_FOUND);
        }
    }

    private class LoginResponse {
        public String token;
        public LoginResponse (String token) {
            this.token = token;
        }
    }

}
