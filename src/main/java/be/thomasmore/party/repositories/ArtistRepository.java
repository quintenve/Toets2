package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Artist;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {

    Optional<Artist> findFirstByIdLessThanOrderByIdDesc(Integer id);

    Optional<Artist> findFirstByOrderByIdDesc();

    Optional<Artist> findFirstByIdGreaterThanOrderByIdAsc(Integer id);

    Optional<Artist> findFirstByOrderByIdAsc();
}
