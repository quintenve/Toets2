package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends CrudRepository<Venue, Integer> {
    Iterable<Venue> findByOutdoor(boolean isOutdoor);
    Iterable<Venue> findByIndoor(boolean isIndoor);
    List<Venue> findByCapacityBetween(int min, int max);
    List<Venue> findByCapacityGreaterThan(int min);
    List<Venue> findAllBy();

    Optional<Venue> findFirstByIdLessThanOrderByIdDesc(Integer id);
    Optional<Venue> findFirstByOrderByIdDesc();
    Optional<Venue> findFirstByIdGreaterThanOrderByIdAsc(Integer id);
    Optional<Venue> findFirstByOrderByIdAsc();

}
