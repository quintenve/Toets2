package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Snack;
import org.springframework.data.repository.CrudRepository;

public interface SnackRepository extends CrudRepository<Snack, Integer> {
}
