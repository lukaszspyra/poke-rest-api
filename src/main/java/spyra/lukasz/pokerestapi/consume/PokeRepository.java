package spyra.lukasz.pokerestapi.consume;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.Pokemon;

interface PokeRepository extends JpaRepository<Pokemon, Long> {
}
