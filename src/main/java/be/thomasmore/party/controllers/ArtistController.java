package be.thomasmore.party.controllers;

import be.thomasmore.party.model.Artist;
import be.thomasmore.party.model.Venue;
import be.thomasmore.party.repositories.ArtistRepository;
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
public class ArtistController {
    final private Logger logger = LoggerFactory.getLogger(ArtistController.class);

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/artistlist")
    public String artistList(Model model) {
        List<Artist> artists = artistRepository.findAllBy();
        model.addAttribute("artists", artists);
        model.addAttribute("nrOfArtists", artists.size());
        model.addAttribute("showFilters", false);
        return "artistlist";
    }

    @GetMapping("/artistlist/filter")
    public String artistListWithFilter(Model model,
                                       @RequestParam(required = false) String keyword) {
        logger.info(String.format("artistListWithFilter -- keyword=%s", keyword));
        List<Artist> artists = artistRepository.findByKeyword(keyword);

        model.addAttribute("artists", artists);
        model.addAttribute("nrOfArtists", artists.size());
        model.addAttribute("showFilters", true);
        model.addAttribute("keyword", keyword);
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

    @GetMapping({"/artistdetails/{id}/prev"})
    public String artistdetailsPrev(Model model, @PathVariable int id) {
        Optional<Artist> prevArtistFromDb = artistRepository.findFirstByIdLessThanOrderByIdDesc(id);
        if (prevArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", prevArtistFromDb.get().getId());
        Optional<Artist> lastArtistFromDb = artistRepository.findFirstByOrderByIdDesc();
        if (lastArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", lastArtistFromDb.get().getId());
        return "artistdetails";
    }

    @GetMapping({"/artistdetails/{id}/next"})
    public String artistdetailsNext(Model model, @PathVariable int id) {
        Optional<Artist> nextArtistFromDb = artistRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
        if (nextArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", nextArtistFromDb.get().getId());
        Optional<Artist> firstArtistFromDb = artistRepository.findFirstByOrderByIdAsc();
        if (firstArtistFromDb.isPresent())
            return String.format("redirect:/artistdetails/%d", firstArtistFromDb.get().getId());
        return "artistdetails";
    }

}
