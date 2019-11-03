package psoft.proj.backend.ajude.users.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersDAO;

    public UsersService(UsersRepository<User, String> usersDAO) {
        super();
        this.usersDAO = usersDAO;
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
}
