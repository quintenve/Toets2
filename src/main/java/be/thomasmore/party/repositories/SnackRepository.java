package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Snack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SnackRepository extends CrudRepository<Snack, Integer> {
    @Query("select s from Snack s where (?1 is null or s.price < ?1) and (?2 is null or s.vegan = ?2)")
    List<Snack> findByFilter(Double maxPrice, Boolean vegan);
}
