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
<<<<<<< HEAD

    public Boolean contaisUrl(String url) {
        return campaignDAO.findById(url).isPresent();
    }

    public Optional<Campaign> getCampaign(String url) {
        return campaignDAO.findById(url);
    }

    public List<Campaign> searchCampaigns(String substring) {
        List<Campaign> campaigns = new LinkedList<>();
        for (Object o : campaignDAO.findAll()) {
            if (((Campaign) o).getName().contains(substring))
                campaigns.add((Campaign) o);
        }
        return campaigns;
    }
=======
>>>>>>> 32ae6b885bf7a3cc124ce899fd481873858e1503
}
