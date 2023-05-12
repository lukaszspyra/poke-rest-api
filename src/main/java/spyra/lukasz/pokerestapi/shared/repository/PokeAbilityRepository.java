package spyra.lukasz.pokerestapi.shared.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeAbility;

import java.util.Optional;

public interface PokeAbilityRepository extends JpaRepository<PokeAbility, Long> {

    Optional<PokeAbility> findFirstByNameAndHidden(String name, boolean hidden);

}
