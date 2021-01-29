package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Animal;
import be.thomasmore.party.repositories.AnimalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class AnimalController {
    private final Logger logger = LoggerFactory.getLogger(AnimalController.class);

    @Autowired
    private AnimalRepository animalRepository;


    @GetMapping({"/animaldetails", "/animaldetails/{id}"})
    public String animalDetails(Model model,
                                @PathVariable(required = false) Integer id) {
        if (id == null) return "animaldetails";

        Optional<Animal> optionalAnimal = animalRepository.findById(id);
        if (optionalAnimal.isPresent()) {
            long nrOfAnimals = animalRepository.count();
            model.addAttribute("animal", optionalAnimal.get());
            model.addAttribute("prevId", id > 1 ? id - 1 : nrOfAnimals);
            model.addAttribute("nextId", id < nrOfAnimals ? id + 1 : 1);
        }
        return "animaldetails";
    }
}

