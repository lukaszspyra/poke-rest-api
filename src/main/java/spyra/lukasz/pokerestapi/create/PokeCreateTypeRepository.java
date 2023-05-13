package spyra.lukasz.pokerestapi.create;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeType;

import java.util.Optional;

interface PokeCreateTypeRepository extends JpaRepository<PokeType, Long> {

    Optional<PokeType> findFirstByName(String name);

}
