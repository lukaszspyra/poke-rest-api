package spyra.lukasz.pokerestapi.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.Pokemon;

public interface PokeRepository extends JpaRepository<Pokemon, Long> {
}
