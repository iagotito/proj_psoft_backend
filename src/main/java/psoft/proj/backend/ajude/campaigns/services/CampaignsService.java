package psoft.proj.backend.ajude.campaigns.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;
import java.util.LinkedList;
import java.util.List;

@Service
public class CampaignsService {
    private CampaignsRepository campaignDAO;
    private JwtService jwtService;

    public CampaignsService (CampaignsRepository campaignsRepository, JwtService jwtService) {
        super ();
        campaignDAO = campaignsRepository;
        this.jwtService = jwtService;
    }

    public Campaign createCampaign (String header, Campaign campaign) throws ServletException {
        campaign.setOwner(jwtService.getTokenSubject(header));
        campaign.setStatus("ativa");
        // Vai ver se a url já existe, se sim, vai aumentando um contador ao fim dela.
        campaign.setUrl(newUrl(campaign.getUrl()));
        return (Campaign) campaignDAO.save(campaign);
    }


    private String newUrl (String url) {
        if (!campaignDAO.existsById(url))
            return url;
        else return newUrlWithNumber(url + "-1", 2);
    }

    private String newUrlWithNumber (String url, int num) {
        if (!campaignDAO.existsById(url))
            return url;
        else {
            int lengthOfNumbers = (url.split("-")[url.split("-").length-1]).length();
            String urlWithoutNumber = url.substring(0, url.length() - lengthOfNumbers-1);
            return newUrlWithNumber(urlWithoutNumber + "-" + num, num+1);
        }
    }

    public List<Campaign> getCampaigns() {
        return campaignDAO.findAll();
    }

    public Boolean contaisUrl(String url) {
        return campaignDAO.findById(url).isPresent();
    }

    public Campaign getCampaign(String url) throws ServletException {
        if (campaignDAO.findById(url).isPresent())
            return (Campaign) campaignDAO.findById(url).get();
        throw new ServletException("Campaign nor found.");
    }

    public List<Campaign> searchCampaigns(String substring) {
        substring = substring.toLowerCase().replace("-"," ");
        List<Campaign> campaigns = new LinkedList<>();
        for (Object o : campaignDAO.findAll()) {
            if (((Campaign) o).getName().toLowerCase().contains(substring))
                campaigns.add((Campaign) o);
        }
        return campaigns;
    }

    public List<Campaign> getTop5Campaigns() {
        List<Campaign> campaigns = campaignDAO.findAll();
        Campaign[] top5 = new Campaign[5];
        for (Campaign c : campaigns)
            addInTop5 (top5, c);
        List<Campaign> ret = new LinkedList<>();
        for (Campaign c : top5) {
            if (c != null)
                ret.add(c);
        }
        return ret;
    }

    private void addInTop5 (Campaign[] top5, Campaign campaign) {
        if (top5 == null)
            top5[0] = campaign;
        else {
            int i = 0;
            boolean insertion = false;
            while (!insertion && i < 5) {
                if (top5[i] != null) {
                    if (isAbove(campaign, top5[i])) {
                        addAndShift(campaign, top5, i);
                        insertion = true;
                    }
                } else {
                    addAndShift (campaign, top5, i);
                    insertion = true;
                }
                i++;
            }
        }
    }

    private void addAndShift (Campaign campaign, Campaign[] top5, int p) {
        Campaign aux1 = campaign;
        Campaign aux2;
        for (int i = p; i < 5; i++) {
            aux2 = top5[i];
            top5[i] = aux1;
            aux1 = aux2;
        }
    }

    // the priority is for the campaign that hasn't reached its goal and has more donations
    private boolean isAbove(Campaign newCampaign, Campaign campaing) {
        int p = 0;
        if (newCampaign.getDonations() >= newCampaign.getGoal())
            p -= 2;
        if (campaing.getDonations() >= campaing.getGoal())
            p += 2;

        if (newCampaign.getDonations() >= campaing.getDonations())
            p++;
        else
            p--;

        return p > 0;
    }
}
