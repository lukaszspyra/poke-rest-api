package spyra.lukasz.pokerestapi.consume;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeStat;

public interface PokeStatRepository extends JpaRepository<PokeStat, Long> {
}
