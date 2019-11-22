package psoft.proj.backend.ajude.campaigns.repositorys;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import psoft.proj.backend.ajude.campaigns.entities.Comment;

import java.io.Serializable;

@Document("Campaign")
public interface CommentsRepository<T, ID extends Serializable> extends MongoRepository<Comment, Integer> {

}

