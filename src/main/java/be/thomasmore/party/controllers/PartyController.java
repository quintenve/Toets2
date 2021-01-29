package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Party;
import be.thomasmore.party.repositories.PartyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class PartyController {
    private final Logger logger = LoggerFactory.getLogger(PartyController.class);

    @Autowired
    private PartyRepository partyRepository;

    @GetMapping("/partylist")
    public String partyList(Model model) {
        Iterable<Party> parties = partyRepository.findAll();
        model.addAttribute("parties", parties);
        return "partylist";
    }

    @GetMapping({"/partydetails", "/partydetails/{id}"})
    public String partyDetails(Model model,
                               @PathVariable(required = false) Integer id) {
        if (id == null) return "partydetails";

        Optional<Party> optionalParty = partyRepository.findById(id);
        if (optionalParty.isPresent()) {
            long nrOfPartys = partyRepository.count();
            model.addAttribute("party", optionalParty.get());
            model.addAttribute("prevId", id > 1 ? id - 1 : nrOfPartys);
            model.addAttribute("nextId", id < nrOfPartys ? id + 1 : 1);
        }
        return "partydetails";
    }

    @GetMapping({"/partydetails/{id}/prev"})
    public String partydetailsPrev(Model model, @PathVariable int id) {
        Optional<Party> prevPartyFromDb = partyRepository.findFirstByIdLessThanOrderByIdDesc(id);
        if (prevPartyFromDb.isPresent())
            return String.format("redirect:/partydetails/%d", prevPartyFromDb.get().getId());
        Optional<Party> lastPartyFromDb = partyRepository.findFirstByOrderByIdDesc();
        if (lastPartyFromDb.isPresent())
            return String.format("redirect:/partydetails/%d", lastPartyFromDb.get().getId());
        return "partydetails";
    }

    @GetMapping({"/partydetails/{id}/next"})
    public String partydetailsNext(Model model, @PathVariable int id) {
        Optional<Party> nextPartyFromDb = partyRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        if (nextPartyFromDb.isPresent())
            return String.format("redirect:/partydetails/%d", nextPartyFromDb.get().getId());
        Optional<Party> firstPartyFromDb = partyRepository.findFirstByOrderByIdAsc();
        if (firstPartyFromDb.isPresent())
            return String.format("redirect:/partydetails/%d", firstPartyFromDb.get().getId());
        return "partydetails";
    }

}
