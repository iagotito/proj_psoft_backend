package psoft.proj.backend.ajude.users.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;

import javax.servlet.ServletException;
import java.rmi.ServerException;
import java.util.List;

@Service
public class UsersService {

    private UsersRepository usersDAO;

    public UsersService(UsersRepository<User, String> usersDAO) {
        super();
        this.usersDAO = usersDAO;
    }

    public User createUser (User user) throws ServerException {
        if (usersDAO.findById(user.getEmail()).isPresent())
            throw new ServerException("E-mail already registered.");
        user.instanciationDonationsIds();
        return (User) usersDAO.save(user);
    }

    public List<User> getUsers() {
        return usersDAO.findAll();
    }

    public User getUser (String email) throws ServletException {
        if (!usersDAO.existsById(email))
            throw new ServletException("User not found.");

        return (User) usersDAO.findById(email).get();
    }

    public boolean userExists(String subject) {
        return usersDAO.findById(subject).isPresent();
    }
}
