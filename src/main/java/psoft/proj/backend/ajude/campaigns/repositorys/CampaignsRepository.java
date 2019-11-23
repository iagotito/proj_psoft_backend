package psoft.proj.backend.ajude.campaigns.repositorys;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;

import java.io.Serializable;

@Document("Campaign")
public interface CampaignsRepository<T, ID extends Serializable> extends MongoRepository<Campaign, Integer> {
}