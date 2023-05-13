package spyra.lukasz.pokerestapi.create;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spyra.lukasz.pokerestapi.shared.Pokemon;

interface PokeCreateRepository extends JpaRepository<Pokemon, Long> {

      @Query("select coalesce(max (p.id), 0) from Pokemon p")
        Long getMaxId();

}
