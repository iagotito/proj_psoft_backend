package psoft.proj.backend.ajude.campaigns.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;

import java.io.Serializable;

@Repository
public interface CampaignsRepository<T, ID extends Serializable> extends JpaRepository<Campaign, Integer> {
}
