package psoft.proj.backend.ajude.campaigns.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignsService {
    private CampaignsRepository campaignDAO;

    public CampaignsService (CampaignsRepository campaignsRepository) {
        super ();
        campaignDAO = campaignsRepository;
    }

    public Campaign createCampaign (String owner, Campaign campaign) {
        campaign.setOwner(owner);
        return (Campaign) campaignDAO.save(campaign);
    }

    public List<Campaign> getCampaigns() {
        return campaignDAO.findAll();
    }
}
