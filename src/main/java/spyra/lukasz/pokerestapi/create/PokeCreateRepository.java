package spyra.lukasz.pokerestapi.create;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.Pokemon;

interface PokeCreateRepository extends JpaRepository<Pokemon, Long> {
}
