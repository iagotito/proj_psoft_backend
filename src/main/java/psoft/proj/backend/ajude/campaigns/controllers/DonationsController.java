package psoft.proj.backend.ajude.campaigns.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import psoft.proj.backend.ajude.auxiliaryEntities.ExceptionResponse;
import psoft.proj.backend.ajude.campaigns.entities.Donation;
import psoft.proj.backend.ajude.campaigns.services.DonationsService;
import psoft.proj.backend.ajude.users.services.JwtService;

import javax.servlet.ServletException;

@RestController
@RequestMapping("/campaigns/{url}/donations")
public class DonationsController {

    private DonationsService donationsService;
    private JwtService jwtService;

    public DonationsController (DonationsService donationsService, JwtService jwtService) {
        super();
        this.donationsService = donationsService;
        this.jwtService = jwtService;
    }

    @CrossOrigin
    @PostMapping("")
    public ResponseEntity<?> donateToCampaign (@RequestHeader("Authorization") String header, @PathVariable String url,
                                               @RequestBody Donation donation) {
        try {
            if (!jwtService.userExists(header))
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse("Header does not correspond to any user."),
                        HttpStatus.NOT_FOUND);
            return new ResponseEntity<Donation>(donationsService.createDonation(header, url, donation),
                    HttpStatus.CREATED);
        } catch (ServletException e) {
            if(e.getMessage().equals("Token inexistente ou mal formatado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.BAD_REQUEST);
            } else if(e.getMessage().equals("Token invalido ou expirado!")){
                return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                        HttpStatus.UNAUTHORIZED);
            }
            return new ResponseEntity<ExceptionResponse>(new ExceptionResponse(e.getMessage()),
                    HttpStatus.NOT_FOUND);
        }
    }
}
