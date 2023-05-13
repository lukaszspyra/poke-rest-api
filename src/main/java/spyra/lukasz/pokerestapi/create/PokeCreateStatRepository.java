package spyra.lukasz.pokerestapi.create;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeStat;

import java.util.Optional;

interface PokeCreateStatRepository extends JpaRepository<PokeStat, Long> {

    Optional<PokeStat> findFirstByNameAndValue(String name, int value);

}
