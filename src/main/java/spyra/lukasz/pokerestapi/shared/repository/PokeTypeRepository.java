package spyra.lukasz.pokerestapi.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeType;

import java.util.Optional;

public interface PokeTypeRepository extends JpaRepository<PokeType, Long> {

    Optional<PokeType> findFirstByName(String name);

}
