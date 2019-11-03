package psoft.proj.backend.ajude.users.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.filters.TokenFilter;

import javax.servlet.ServletException;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    private final String TOKEN_KEY = "login do batman";
    private UsersService usersService;

    public JwtService (UsersService usersService) {
        super ();
        this.usersService = usersService;
    }

    public String generateToken(String email) {
        return Jwts.builder().setSubject(email)
                .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
                .setExpiration(new Date(System.currentTimeMillis() + 1 * 60 * 1000)).compact();
    }

    public boolean userExists(String authorizationHeader) throws ServletException {
        String subject = getTokenSubject(authorizationHeader);

        return usersService.getUser(subject).isPresent();
    }

    public boolean userHavePermission(String authorizationHeader, String email) throws ServletException {
        String subject = getTokenSubject(authorizationHeader);

        Optional<User> optUser = usersService.getUser(subject);
        return optUser.isPresent() && optUser.get().getEmail().equals(email);
    }

    private String getTokenSubject(String authorizationHeader) throws ServletException {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ServletException("Token inexistente ou mal formatado!");
        }
        // Extraindo apenas o token do cabecalho.
        String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);

        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey("login do batman").parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException e) {
            throw new ServletException("Token invalido ou expirado!");
        }
        return subject;
    }


}

