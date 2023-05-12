package spyra.lukasz.pokerestapi.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeStat;

import java.util.Optional;

public interface PokeStatRepository extends JpaRepository<PokeStat, Long> {

    Optional<PokeStat> findFirstByNameAndValue(String name, int value);

}
