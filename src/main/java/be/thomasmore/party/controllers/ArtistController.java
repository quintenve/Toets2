package be.thomasmore.party.controllers;

import be.thomasmore.party.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

}
