package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VenueController {
    @GetMapping("/venuedetails")
    public String venuedetails(Model model) {
        Venue venue = new Venue("BoesjKammeree", "https://www.facebook.com/boesjkammeree/");
        model.addAttribute("venue", venue);
        return "venuedetails";
    }

}
