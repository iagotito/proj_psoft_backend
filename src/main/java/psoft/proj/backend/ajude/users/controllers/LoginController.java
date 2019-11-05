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
    public ResponseEntity<LoginResponse> authenticate(@RequestBody User user) throws ServletException {

        // Recupera o usuario
        Optional<User> authUsuario = usersService.getUser(user.getEmail());

        // verificacoes
        if (authUsuario.isEmpty())
            return new ResponseEntity("Usuário não encontrado.", HttpStatus.NOT_FOUND);
        if (!authUsuario.get().getPassword().equals(user.getPassword()))
            return new ResponseEntity("Senha inválida.", HttpStatus.FORBIDDEN);

        String token = jwtService.generateToken(authUsuario.get().getEmail());
        return new ResponseEntity(new LoginResponse(token), HttpStatus.OK);
    }

    private class LoginResponse {
        public String token;

        public LoginResponse (String token) {
            this.token = token;
        }
    }

}
