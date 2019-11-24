package psoft.proj.backend.ajude.users.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.entities.Donation;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;
import psoft.proj.backend.ajude.campaigns.repositorys.DonationsRepository;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;

import javax.servlet.ServletException;
import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private UsersRepository usersDAO;
    private CampaignsRepository campaignsDAO;
    private DonationsRepository donationsDAO;

    public UsersService(UsersRepository<User, String> usersDAO, CampaignsRepository campaignsDAO,
                        DonationsRepository donationsDAO) {
        super();
        this.usersDAO = usersDAO;
        this.campaignsDAO = campaignsDAO;
        this.donationsDAO = donationsDAO;
    }

    public User createUser (User user) throws ServerException {
        if (usersDAO.findById(user.getEmail()).isPresent())
            throw new ServerException("E-mail already registered.");
        user.instanciationDonationsIds();
        return (User) usersDAO.save(user);
    }

    public List<User> getUsers() {
        return usersDAO.findAll();
    }

    public List<Campaign> getUserCampaigns(String email) throws ServletException {
        List<Campaign> campaigns = campaignsDAO.findAll();
        return this.filterByOwner(campaigns, email);
    }

    public List<Campaign> filterByOwner(List<Campaign> campaigns, String email){
        List<Campaign> userCampaigns = new ArrayList<>();

        for(int i = 0; i < campaigns.size(); i++){
            if(campaigns.get(i).getOwner().equals(email)){
                userCampaigns.add(campaigns.get(i));
            }
        }
        Optional<User> user = usersDAO.findById(email);

        if(user.isPresent()){
            List<String> donations = user.get().getDonationsIds();

            for(int j = 0; j < donations.size(); j++){
                Optional<Donation> donation = (donationsDAO.findById(donations.get(j)));
                if(donation.isPresent()){
                    String id = donation.get().getCampaign();
                    Optional<Campaign> campaign = campaignsDAO.findById(id);
                    if(campaign.isPresent() && !userCampaigns.contains(campaign.get())){
                        userCampaigns.add(campaign.get());
                    }
                }
            }
        }
        return userCampaigns;
    }

    public User getUser (String email) throws ServletException {
        if (!usersDAO.existsById(email))
            throw new ServletException("User not found.");

        return (User) usersDAO.findById(email).get();
    }

    public boolean userExists(String subject) {
        return usersDAO.findById(subject).isPresent();
    }
}
