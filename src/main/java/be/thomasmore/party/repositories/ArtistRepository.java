package be.thomasmore.party.repositories;

import be.thomasmore.party.model.Artist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {
    List<Artist> findAllBy();
    List<Artist> findByArtistNameContainingIgnoreCase(String keyword);

    Optional<Artist> findFirstByIdLessThanOrderByIdDesc(Integer id);
    Optional<Artist> findFirstByOrderByIdDesc();
    Optional<Artist> findFirstByIdGreaterThanOrderByIdAsc(Integer id);
    Optional<Artist> findFirstByOrderByIdAsc();

    @Query("select a from Artist a WHERE " +
            ":keyword IS NULL OR " +
            "(UPPER(a.artistName) LIKE UPPER(CONCAT('%', :keyword, '%'))) OR " +
            "(UPPER(a.bio) LIKE UPPER(CONCAT('%', :keyword, '%'))) OR " +
            "(UPPER(a.genre) LIKE UPPER(CONCAT('%', :keyword, '%'))) OR " +
            "(UPPER(a.portfolio) LIKE UPPER(CONCAT('%', :keyword, '%')))")
    List<Artist> findByKeyword(@Param("keyword")String keyword);
}
