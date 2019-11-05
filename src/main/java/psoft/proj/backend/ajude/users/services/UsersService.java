package psoft.proj.backend.ajude.users.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;

import javax.servlet.ServletException;
import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersDAO;
    private JwtService jwtService;

    public UsersService(UsersRepository<User, String> usersDAO, JwtService jwtService) {
        super();
        this.usersDAO = usersDAO;
        this.jwtService = jwtService;
    }

    public User postUser (User user) throws ServerException {
        if (!usersDAO.findById(user.getEmail()).isPresent())
            return (User) usersDAO.save(user);
        throw new ServerException("E-mail already registered.");
    }

    public List<User> getUsers() {
        return usersDAO.findAll();
    }

    public Optional<User> getUser (String email) {
        return usersDAO.findById(email);
    }

    public void deleteUsers() {
        usersDAO.deleteAll();
    }

    public User getUserByHeader(String header) throws ServletException {
        return (User) usersDAO.findById(jwtService.getTokenSubject(header)).get();
    }
}
