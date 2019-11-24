package psoft.proj.backend.ajude.campaigns.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.auxiliaryEntities.campaignsComparators.DeadlineCompare;
import psoft.proj.backend.ajude.auxiliaryEntities.campaignsComparators.DonationsCompare;
import psoft.proj.backend.ajude.auxiliaryEntities.campaignsComparators.LikesCompare;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;
import java.util.*;

@Service
public class CampaignsService {
    private CampaignsRepository campaignDAO;
    private JwtService jwtService;

    public CampaignsService (CampaignsRepository campaignsRepository, JwtService jwtService) {
        super ();
        this.campaignDAO = campaignsRepository;
        this.jwtService = jwtService;
    }

    public Campaign createCampaign (String header, Campaign campaign) throws ServletException {
        campaign.setOwner(jwtService.getTokenSubject(header));
        campaign.setStatus("ativa");
        campaign.instanciationComments();
        campaign.instanciationLikes();
        campaign.instanciationDonationsIds();

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

    public List<Campaign> filterCampaigns (String sort, String status, String substring) {
        List<Campaign> campaigns = campaignDAO.findAll();
        List<Campaign> filteredCampaigns = new ArrayList<>();
        // first ew filter the campaigns by the status filter
        for (Campaign c : campaigns) {
            if (status.equals("all") || parseStatus(c.getStatus()).equals(status)) {
                if (substring.equals(""))
                    filteredCampaigns.add(c);
                else if (c.getName().contains(substring))
                    filteredCampaigns.add(c);
            }
        }

        // then we sort based on the sort parameter
        if (sort.equals("deadline"))
            Collections.sort(filteredCampaigns, new DeadlineCompare());
        else if (sort.equals("donations"))
            Collections.sort(filteredCampaigns, new DonationsCompare());
        else
            Collections.sort(filteredCampaigns, new LikesCompare());
        // todo filtrar (talvez antes de adicionar em filtered campaigns) as campanhas que já foram concluídas
        // todo filtrar (das campanhas ja filtradas) os comentarios que devem ser exibidos.
        return filteredCampaigns;
    }

    public Boolean contaisUrl(String url) {
        return campaignDAO.findById(url).isPresent();
    }

    public Campaign getCampaign(String url) throws ServletException {
        if (campaignDAO.findById(url).isPresent())
            return (Campaign) campaignDAO.findById(url).get();
        throw new ServletException("Campaign not found.");
    }

    public List<Campaign> searchCampaigns(String substring) {
        substring = substring.toLowerCase().replace("-"," ");
        List<Campaign> campaigns = new LinkedList<Campaign>();
        for (Object o : campaignDAO.findAll()) {
            if (((Campaign) o).getName().toLowerCase().contains(substring))
                campaigns.add((Campaign) o);
        }
        return campaigns;
    }

    public List<Campaign> getTop5Campaigns(String sort, String status, String substring) {
        List<Campaign> filteredCampaigns = filterCampaigns(sort, status, substring);

        if (filteredCampaigns.size() < 5)
            return filteredCampaigns;
        else
            return filteredCampaigns.subList(0, 5);
    }

    private String parseStatus (String status) {
        if (status.equals("ativa")) return "active";
        else if (status.equals("concluída")) return "concluded";
        else return "expired";
    }

    public Campaign toLike(String url, String header) throws ServletException {
        String email = jwtService.getTokenSubject(header);
        Campaign newCampaign = this.getCampaign(url);

        if(newCampaign.getLikes().contains(email)){
            newCampaign.removeLike(email);
        } else {
            newCampaign.setLikes(email);
        }

        campaignDAO.delete(this.getCampaign(url));
        campaignDAO.save(newCampaign);

        return newCampaign;
    }
}
