package psoft.proj.backend.ajude.users.repositorys;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import psoft.proj.backend.ajude.users.entities.User;

import java.io.Serializable;

@Document("User")
public interface UsersRepository<T, ID extends Serializable> extends MongoRepository<User, String> {
}
