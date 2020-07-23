package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.repositories.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artistlist")
    public String artistList(Model model) {
        Iterable<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "artistlist";
    }

    @GetMapping({ "/artistdetails/{id}", "/artistdetails"})
    public String artistDetails(Model model,
                                @PathVariable(required = false) Integer id) {
        if (id == null) return "artistdetails";

        Optional<Artist> optionalArtist = artistRepository.findById(id);
        //noinspection OptionalIsPresent
        if (optionalArtist.isPresent()) {
            model.addAttribute("artist", optionalArtist.get());
        }
        return "artistdetails";
    }

}
