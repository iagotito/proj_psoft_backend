package psoft.proj.backend.ajude.users.controllers;

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
    public LoginResponse authenticate(@RequestBody User user) throws ServletException {

        // Recupera o usuario
        Optional<User> authUsuario = usersService.getUser(user.getEmail());

        // verificacoes
        if (authUsuario.isEmpty())
            throw new ServletException("Usuario nao encontrado!");
        if (!authUsuario.get().getPassword().equals(user.getPassword()))
            throw new ServletException("Senha invalida!");

        String token = jwtService.generateToken(authUsuario.get().getEmail());
        return new LoginResponse(token);
    }

    private class LoginResponse {
        public String token;

        public LoginResponse(String token) {
            this.token = token;
        }
    }

}
