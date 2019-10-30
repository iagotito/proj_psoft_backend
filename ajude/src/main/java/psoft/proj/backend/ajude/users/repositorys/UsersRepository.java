package psoft.proj.backend.ajude.users.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.proj.backend.ajude.users.entities.User;

import java.io.Serializable;

@Repository
public interface UsersRepository<T, ID extends Serializable> extends JpaRepository<User, String> {

}
