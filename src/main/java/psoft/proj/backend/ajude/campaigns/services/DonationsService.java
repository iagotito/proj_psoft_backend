package psoft.proj.backend.ajude.campaigns.services;

import org.springframework.stereotype.Service;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.entities.Donation;
import psoft.proj.backend.ajude.campaigns.repositorys.CampaignsRepository;
import psoft.proj.backend.ajude.campaigns.repositorys.DonationsRepository;
import psoft.proj.backend.ajude.users.entities.User;
import psoft.proj.backend.ajude.users.repositorys.UsersRepository;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class DonationsService {

    private JwtService jwtService;
    private CampaignsRepository campaignsDAO;
    private UsersRepository usersDAO;
    private DonationsRepository donationDAO;
    private CampaignsService campaignsService;

    public DonationsService (CampaignsRepository campaignsRepository, JwtService jwtService,
                             CampaignsService campaignsService, DonationsRepository donationsRepository,
                             UsersRepository usersRepository) {
        super ();
        this.campaignsDAO = campaignsRepository;
        this.jwtService = jwtService;
        this.campaignsService = campaignsService;
        this.donationDAO = donationsRepository;
        this.usersDAO = usersRepository;
    }

    public Donation createDonation (String header, String url, Donation donation) throws ServletException {
        if (!jwtService.userExists(header))
            throw new ServletException("User not found.");
        if (!campaignsService.contaisUrl(url))
            throw new ServletException("Campaign not found.");

        donation.setOwner(jwtService.getTokenSubject(header));
        donation.setCampaign(url);

        LocalDate currentDate = LocalDate.now();
        donation.setDate(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        donationDAO.save(donation);

        Optional<Campaign> newCampaign = campaignsDAO.findById(donation.getCampaign());

        if(newCampaign.isPresent()){
            newCampaign.get().setDonationsIds(donation.getId());
            newCampaign.get().setDonations(donation.getAmount());

            if(newCampaign.get().getGoal() <= newCampaign.get().getDonations()){
                newCampaign.get().setStatus("concluída");
            }

            campaignsDAO.delete(campaignsDAO.findById(donation.getCampaign()).get());
            campaignsDAO.save(newCampaign.get());
        }

        Optional<User> newUser = usersDAO.findById(donation.getOwner());

        if(newUser.isPresent()){
            newUser.get().setDonationsIds(donation.getId());

            usersDAO.delete(usersDAO.findById(donation.getOwner()).get());
            usersDAO.save(newUser.get());
        }

        return donation;
    }

}
