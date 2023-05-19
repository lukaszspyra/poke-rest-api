package spyra.lukasz.pokerestapi.consume;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeStat;

import java.util.Optional;

interface PokeStatRepository extends JpaRepository<PokeStat, Long> {

    Optional<PokeStat> findFirstByNameAndPoints(String name, int points);

}
