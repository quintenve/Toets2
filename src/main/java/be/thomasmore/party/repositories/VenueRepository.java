package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends CrudRepository<Venue, Integer> {

    @Query("SELECT v FROM Venue v WHERE " +
            "(:min IS NULL OR :min <= v.capacity) AND " +
            "(:max IS NULL OR v.capacity <= :max)")
    List<Venue> findByCapacityBetween(@Param("min") Integer min,
                                      @Param("max") Integer max);

    @Query("SELECT v FROM Venue v WHERE :min IS NULL OR :min <= v.capacity")
    List<Venue> findByCapacityGreaterThan(@Param("min") Integer min);

    Optional<Venue> findFirstByIdLessThanOrderByIdDesc(Integer id);

    Optional<Venue> findFirstByOrderByIdDesc();

    Optional<Venue> findFirstByIdGreaterThanOrderByIdAsc(Integer id);

    Optional<Venue> findFirstByOrderByIdAsc();


}
