package psoft.proj.backend.ajude.campaigns.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.proj.backend.ajude.campaigns.entities.Comment;

import java.io.Serializable;

@Repository
public interface CommentsRepository<T, ID extends Serializable> extends JpaRepository<Comment, Integer> {
}
