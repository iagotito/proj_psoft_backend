package psoft.proj.backend.ajude.campaigns.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.auxiliaryEntities.ExceptionResponse;
import psoft.proj.backend.ajude.campaigns.entities.Donation;
import psoft.proj.backend.ajude.campaigns.services.DonationsService;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/campaigns/{url}/donations")
public class DonationsController {

    private DonationsService donationsService;

    public DonationsController (DonationsService donationsService) {
        super();
        this.donationsService = donationsService;
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> donateToCampaign (@RequestHeader("Authorization") String header, @PathVariable String url,
                                               @RequestBody Donation donation) {
        try {
            return new ResponseEntity<Donation>(donationsService.createDonation(header, url, donation),
                    HttpStatus.CREATED);
        } catch (ServletException e) {
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
