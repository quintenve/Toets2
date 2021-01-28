package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Venue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VenueRepository extends CrudRepository<Venue, Integer> {

    @Query("SELECT v FROM Venue v WHERE " +
            "(:minCapacity IS NULL OR :minCapacity <= v.capacity) AND " +
            "(:maxCapacity IS NULL OR v.capacity <= :maxCapacity) AND " +
            "(:maxDistance IS NULL OR v.distanceFromPublicTransportInKm <= :maxDistance) AND " +
            "(:foodProvided IS NULL OR v.foodProvided = :foodProvided) AND " +
            "(:indoor IS NULL OR v.indoor=:indoor) AND " +
            "(:outdoor IS NULL OR v.outdoor=:outdoor) ")
    List<Venue> findByFilter(@Param("minCapacity") Integer minCapacity,
                             @Param("maxCapacity") Integer maxCapacity,
                             @Param("maxDistance") Integer maxDistance,
                             @Param("foodProvided") Boolean foodProvided,
                             @Param("indoor") Boolean indoor,
                             @Param("outdoor") Boolean outdoor);


    Optional<Venue> findFirstByIdLessThanOrderByIdDesc(Integer id);

    Optional<Venue> findFirstByOrderByIdDesc();

    Optional<Venue> findFirstByIdGreaterThanOrderByIdAsc(Integer id);

    Optional<Venue> findFirstByOrderByIdAsc();


}
