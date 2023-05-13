package spyra.lukasz.pokerestapi.consume;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeType;

import java.util.Optional;

interface PokeTypeRepository extends JpaRepository<PokeType, Long> {

    Optional<PokeType> findFirstByName(String name);

}
