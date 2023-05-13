package spyra.lukasz.pokerestapi.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spyra.lukasz.pokerestapi.shared.Pokemon;

public interface PokeRepository extends JpaRepository<Pokemon, Long> {

    @Query("select coalesce(max (id), 0) from Pokemon")
    Long getMaxId();

}
