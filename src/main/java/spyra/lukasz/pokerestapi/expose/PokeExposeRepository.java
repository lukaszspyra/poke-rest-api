package spyra.lukasz.pokerestapi.expose;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.Pokemon;

public interface PokeExposeRepository extends JpaRepository<Pokemon, Long> {
}
