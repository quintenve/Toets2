package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.VenueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class VenueController {
    private final Logger logger = LoggerFactory.getLogger(VenueController.class);

    @Autowired
    private VenueRepository venueRepository;

    @GetMapping({"/venuelist"})
    public String venueList(Model model) {
        logger.info("venueList");
        Iterable<Venue> venues = venueRepository.findAll();
        long nrOfVenues = venueRepository.count();
        model.addAttribute("venues", venues);
        model.addAttribute("nrOfVenues", nrOfVenues);
        model.addAttribute("showFilters", false);
        return "venuelist";
    }

    @GetMapping({"/venuelist/filter"})
    public String venueListWithFilter(Model model,
                                      @RequestParam(required = false) Integer minCapacity) {
        logger.info(String.format("venueListWithFilter -- min=%d", minCapacity));
        List<Venue> venues;
        if (minCapacity != null)
            venues = venueRepository.findByCapacityGreaterThan(minCapacity);
        else
            venues = venueRepository.findAllBy();

        model.addAttribute("venues", venues);
        model.addAttribute("nrOfVenues", venues.size());
        model.addAttribute("showFilters", true);
        model.addAttribute("minCapacity", minCapacity);
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

    /*
     * prev/next buttons - second solution:
     * find correct venue and get its id
     * then send a REDIRECT to the browser for the normal url with this id
     *
     * if rows were removed from the db this will still work.
     * we always see a logical url
     *
     * disadvantage: we have to access the database 2x (or 3x if the current one is the first one)
     */

    @GetMapping({"/venuedetails/{id}/prev"})
    public String venuedetailsPrev(Model model, @PathVariable int id) {
        Optional<Venue> prevVenueFromDb = venueRepository.findFirstByIdLessThanOrderByIdDesc(id);
        if (prevVenueFromDb.isPresent())
            return String.format("redirect:/venuedetails/%d", prevVenueFromDb.get().getId());
        Optional<Venue> lastVenueFromDb = venueRepository.findFirstByOrderByIdDesc();
        if (lastVenueFromDb.isPresent())
            return String.format("redirect:/venuedetails/%d", lastVenueFromDb.get().getId());
        return "venuedetails";
    }

    @GetMapping({"/venuedetails/{id}/next"})
    public String venuedetailsNext(Model model, @PathVariable int id) {
        Optional<Venue> nextVenueFromDb = venueRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        if (nextVenueFromDb.isPresent())
            return String.format("redirect:/venuedetails/%d", nextVenueFromDb.get().getId());
        Optional<Venue> firstVenueFromDb = venueRepository.findFirstByOrderByIdAsc();
        if (firstVenueFromDb.isPresent())
            return String.format("redirect:/venuedetails/%d", firstVenueFromDb.get().getId());
        return "venuedetails";
    }

}
