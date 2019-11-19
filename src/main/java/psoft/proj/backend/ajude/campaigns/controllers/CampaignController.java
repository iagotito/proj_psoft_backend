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
}
