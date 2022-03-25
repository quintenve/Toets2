package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Snack;
import be.thomasmore.party.repositories.SnackRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        return "snacklist";
    }
}
