package spyra.lukasz.pokerestapi.search;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.Pokemon;

interface PokeSearchRepository extends JpaRepository<Pokemon, Long> {
}
