package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class VenueController {
    @Autowired
    private VenueRepository venueRepository;

    @GetMapping({"/venuelist", "/venuelist/outdoor", "/venuelist/outdoor/all"})
    public String venuelist(Model model) {
        final Iterable<Venue> allVenues = venueRepository.findAll();
        model.addAttribute("venues", allVenues);
        return "venuelist";
    }

    @GetMapping("/venuelist/outdoor/{filter}")
    public String venueListOutdoorYes(Model model, @PathVariable String filter) {
        final Iterable<Venue> venues = venueRepository.findByOutdoor(filter.equals("yes"));
        model.addAttribute("venues", venues);
        model.addAttribute("filterOutdoor", filter.equals("yes") ? "yes" : "no");
        return "venuelist";
    }

    @GetMapping("/venuelist/indoor/{filter}")
    public String venueListIndoor(Model model,
                                  @PathVariable String filter) {
        Iterable<Venue> venues = venueRepository.findByIndoor(filter.equals("yes"));
        model.addAttribute("venues", venues);
        model.addAttribute("filterIndoor", filter.equals("yes") ? "yes" : "no");
        return "venuelist";
    }

    @GetMapping("/venuelist/size/{filter}")
    public String venueListSize(Model model,
                                @PathVariable String filter) {
        Iterable<Venue> venues;
        switch (filter) {
            case "S":
                venues = venueRepository.findByCapacityBetween(0, 200);
                break;
            case "M":
                venues = venueRepository.findByCapacityBetween(200, 600);
                break;
            case "L":
                venues = venueRepository.findByCapacityGreaterThan(600);
                break;
            default:
                venues = venueRepository.findAll();
                filter = null;
                break;
        }
        model.addAttribute("venues", venues);
        model.addAttribute("filterSize", filter);
        return "venuelist";
    }

    @GetMapping({"/venuedetails/{id}", "/venuedetails"})
    public String venuedetails(Model model, @PathVariable(required = false) Integer id) {
        if (id == null) return "venuedetails";

        Optional<Venue> venueFromDb = venueRepository.findById(id);
        //noinspection OptionalIsPresent
        if (venueFromDb.isPresent()) {
            model.addAttribute("venue", venueFromDb.get());
        }
        return "venuedetails";
    }

}
