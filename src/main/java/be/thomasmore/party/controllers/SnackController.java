package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Party;
import be.thomasmore.party.model.Snack;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.SnackRepository;
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
public class SnackController {
    private Logger logger = LoggerFactory.getLogger(SnackController.class);

    @Autowired
    private SnackRepository snackRepository;

    @GetMapping({"/snacklist", "/snacklist/{extrawoord}"})
    public String snackList(Model model, @PathVariable (required = false) String extrawoord){
        logger.info("snackList");
        Iterable<Snack> snacks = snackRepository.findAll();
        long nrOfSnacks = snackRepository.count();
        model.addAttribute("snacks", snacks);
        model.addAttribute("nrOfSnacks", nrOfSnacks);
        model.addAttribute("showFilters", false);
        return "snacklist";
    }

    @GetMapping({"/snackdetails/{snackid}", "/snackdetails"})
    public String snackDetails(Model model, @PathVariable (required = false) Integer snackid){
        if (snackid == null) return "snackdetails";

        int nextId = snackid+1;
        int previousId = snackid-1;
        long dbCount = snackRepository.count();
        if (nextId > dbCount){
            nextId = 1;
        }
        if (previousId < 1){
            previousId = Integer.parseInt(Long.toString(dbCount));
        }

        Optional<Snack> snackFromDb = snackRepository.findById(snackid);
        //noinspection OptionalIsPresent
        if (snackFromDb.isPresent()) {
            model.addAttribute("snack", snackFromDb.get());
        } else {
            String message = "index number between 1 and 7";
            model.addAttribute("message", message);
        }

        model.addAttribute("nextId", nextId);
        model.addAttribute("previousId", previousId);

        return "snackdetails";
    }

    @GetMapping("/snacklist/filter")
    public String snackListWithFilter(Model model, @RequestParam (required = false) Double maxPrice, @RequestParam (required = false) String vegan,
                                      @RequestParam (required = false) String sideDishIncluded) {

        Boolean veganBoolean;
        if (vegan == null) {
            veganBoolean = null;
        } else if (vegan.equals("yes")) {
            veganBoolean = true;
        } else if (vegan.equals("no")) {
            veganBoolean = false;
        } else {
            veganBoolean = null;
        }
        List<Snack> snacks;
        boolean sideBoolean = false;
        if (sideDishIncluded == null) {
            snacks = snackRepository.findByFilter(maxPrice, veganBoolean);
        } else if (sideDishIncluded.equals("yes")){
            snacks = snackRepository.findByFilterSideDishIncl(maxPrice, veganBoolean);
            sideBoolean = true;
        } else {
            snacks = snackRepository.findByFilter(maxPrice, veganBoolean);
            model.addAttribute("sideDishIncluded", "no");
        }


        model.addAttribute("snacks", snacks);
        model.addAttribute("nrOfSnacks", snacks.size());
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sideBoolean", sideBoolean);
        model.addAttribute("vegan", vegan);
        model.addAttribute("showFilters", true);
        return "snacklist";
    }
}
