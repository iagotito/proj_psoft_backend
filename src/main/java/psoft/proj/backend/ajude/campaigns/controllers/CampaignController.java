package psoft.proj.backend.ajude.campaigns.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.auxiliaryEntities.ExceptionResponse;
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

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<?> createCampaign (@RequestHeader("Authorization") String header,
                                                    @RequestBody Campaign campaign) {
        try {
            if (!jwtService.userExists(header))
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("Header does not correspond to any user."),
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<Campaign>(campaignsService.createCampaign(header, campaign),
                    HttpStatus.CREATED);
        } catch (ServletException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.FORBIDDEN);
        }
    }

    @CrossOrigin
    @GetMapping("")
    public ResponseEntity<List<Campaign>> getCampaigns () {
        return new ResponseEntity<List<Campaign>>(campaignsService.getCampaigns(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = {"/all/filter-by/{sort}/{status}",
            "/all/filter-by/{sort}/{status}/{substring}"})
    public ResponseEntity<List<Campaign>> getCampaigns (@PathVariable("sort") String sort,
                                                        @PathVariable("status") String status,
                                                        @PathVariable("substring") Optional<String> substring) {
        if (substring.isPresent())
            return new ResponseEntity<List<Campaign>>(campaignsService.filterCampaigns(sort, status, substring.get()), HttpStatus.OK);
        else
            return new ResponseEntity<List<Campaign>>(campaignsService.filterCampaigns(sort, status, ""), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(path = {"/top-5/filter-by/{sort}/{status}",
            "/top-5/filter-by/{sort}/{status}/{substring}"})
    public ResponseEntity<List<Campaign>> getTop5Campaigns (@PathVariable("sort") String sort,
                                                            @PathVariable("status") String status,
                                                            @PathVariable("substring") Optional<String> substring) {
        if (substring.isPresent())
            return new ResponseEntity<List<Campaign>>(campaignsService.getTop5Campaigns(sort, status, substring.get()), HttpStatus.OK);
        else
            return new ResponseEntity<List<Campaign>>(campaignsService.getTop5Campaigns(sort, status, ""), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/{url}")
    public ResponseEntity<?> getCampaign (@PathVariable String url) {
        try {
            return new ResponseEntity<Campaign>(campaignsService.getCampaign(url),
                    HttpStatus.OK);
        } catch (ServletException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/{url}/donations")
    public ResponseEntity<?> getDonations (@PathVariable String url) {
        try {
            return new ResponseEntity<Campaign>(campaignsService.getCampaign(url),
                    HttpStatus.OK);
        } catch (ServletException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/contains-url/{url}")
    public ResponseEntity<Boolean> containsUrl (@PathVariable String url) {
        return new ResponseEntity<Boolean>(campaignsService.contaisUrl(url), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/search/{substring}")
    public ResponseEntity<List<Campaign>> searchCampaigns (@PathVariable String substring) {
        return new ResponseEntity<List<Campaign>>(campaignsService.searchCampaigns(substring), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{url}/likes")
    public ResponseEntity<?> toLike (@PathVariable String url, @RequestHeader("Authorization") String header){
        try {
            return new ResponseEntity<>(campaignsService.toLike(url, header), HttpStatus.OK);
        } catch(ServletException e) {
            if(e.getMessage().equals("Campaign not found.")){
                return new ResponseEntity<>(new ExceptionResponse("Campaign not found."), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(new ExceptionResponse("Token invalido ou expirado!"), HttpStatus.FORBIDDEN);
            }
        }
    }

}
