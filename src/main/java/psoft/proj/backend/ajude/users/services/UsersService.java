package psoft.proj.backend.ajude.users.services;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.auxiliaryEntities.campaignsComparators.DateCompare;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.entities.Donation;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;
import psoft.proj.backend.ajude.campaigns.repositorys.DonationsRepository;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;

import javax.servlet.ServletException;
import java.rmi.ServerException;
import java.util.*;

@Service
public class UsersService {

    private UsersRepository usersDAO;
    private CampaignsRepository campaignsDAO;
    private DonationsRepository donationsDAO;
    private EmailService emailService;

    public UsersService(UsersRepository<User, String> usersDAO, CampaignsRepository campaignsDAO,
                        DonationsRepository donationsDAO, EmailService emailService) {
        super();
        this.usersDAO = usersDAO;
        this.campaignsDAO = campaignsDAO;
        this.donationsDAO = donationsDAO;
        this.emailService = emailService;
    }

    public User createUser (User user) throws ServerException {
        if (usersDAO.findById(user.getEmail()).isPresent())
            throw new ServerException("E-mail already registered.");
        user.instanciationDonationsIds();
        try {
            emailService.sendEmail(user.getEmail());
        } catch (ServletException e){
            throw new ServerException("Invalid e-mail");
        }


        return (User) usersDAO.save(user);
    }

    public List<User> getUsers() {
        return usersDAO.findAll();
    }

    public List<Campaign> getUserCampaigns(String email, String substring) throws ServletException {
        List<Campaign> campaigns = campaignsDAO.findAll();
        return this.filterCampaignsByOwner(campaigns, email, substring);
    }

    public List<Campaign> getUserCampaignsDonated(String email, String substring) throws ServletException {
        List<Campaign> campaigns = campaignsDAO.findAll();
        return this.filterCampaignsDonatedByOwner(campaigns, email, substring);
    }

    public List<Campaign> filterCampaignsByOwner(List<Campaign> campaigns, String email, String substring){

        List<Campaign> userCampaigns = new ArrayList<>();

        for(int i = 0; i < campaigns.size(); i++){
            if(campaigns.get(i).getOwner().equals(email)){
                if (substring.equals(""))
                    userCampaigns.add(campaigns.get(i));
                else if (campaigns.get(i).getName().contains(substring))
                    userCampaigns.add(campaigns.get(i));
            }
        }

        Collections.sort(userCampaigns, new DateCompare());

        return userCampaigns;
    }

    public List<Campaign> filterCampaignsDonatedByOwner(List<Campaign> campaigns, String email, String substring) {
        Optional<User> user = usersDAO.findById(email);
        List<Campaign> userCampaignsDonated = new ArrayList<>();

        if(user.isPresent()){
            List<String> donations = user.get().getDonationsIds();

            for(int j = 0; j < donations.size(); j++){
                Optional<Donation> donation = (donationsDAO.findById(donations.get(j)));
                if(donation.isPresent()){
                    String id = donation.get().getCampaign();
                    Optional<Campaign> campaign = campaignsDAO.findById(id);
                    if(campaign.isPresent() && !userCampaignsDonated.contains(campaign.get())){
                        if (substring.equals(""))
                            userCampaignsDonated.add(campaign.get());
                        else if (campaign.get().getName().contains(substring))
                            userCampaignsDonated.add(campaign.get());
                    }
                }
            }
        }
        Collections.sort(userCampaignsDonated, new DateCompare());
        return userCampaignsDonated;
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
