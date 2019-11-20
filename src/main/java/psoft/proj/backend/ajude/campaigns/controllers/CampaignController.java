package psoft.proj.backend.ajude.campaigns.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.campaigns.entities.Campaign;
import psoft.proj.backend.ajude.campaigns.services.CampaignsService;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    private CampaignsService campaignsService;
    private JwtService jwtService;

    public CampaignController (CampaignsService campaignsService, JwtService jwtService) {
        super();
        this.campaignsService = campaignsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/create")
    public ResponseEntity<Campaign> createCampaign (@RequestHeader("Authorization") String header,
                                                    @RequestBody Campaign campaign) {
        String user;
        try {
            user = jwtService.getTokenSubject(header);
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(campaignsService.createCampaign(user, campaign),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Campaign>> getCampaigns () {
        return new ResponseEntity<>(campaignsService.getCampaigns(), HttpStatus.OK);
    }
<<<<<<< HEAD

    @GetMapping("/{url}")
    public ResponseEntity<Campaign> getCampaign (@PathVariable String url) {
        Optional<Campaign> campaign = campaignsService.getCampaign(url);
        if (campaign.isPresent())
            return new ResponseEntity<>(campaign.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/contains-url/{url}")
    public ResponseEntity<Boolean> containsUrl (@PathVariable String url) {
        return new ResponseEntity<>(campaignsService.contaisUrl(url), HttpStatus.OK);
    }

    @GetMapping("/search/{substring}")
    public ResponseEntity<List<Campaign>> searchCampaigns (@PathVariable String substring) {
        return new ResponseEntity<>(campaignsService.searchCampaigns(substring), HttpStatus.OK);
    }

=======
>>>>>>> 32ae6b885bf7a3cc124ce899fd481873858e1503
}
