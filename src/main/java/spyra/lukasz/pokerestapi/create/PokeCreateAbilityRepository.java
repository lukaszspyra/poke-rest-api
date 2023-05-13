package spyra.lukasz.pokerestapi.create;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeAbility;

import java.util.Optional;

interface PokeCreateAbilityRepository extends JpaRepository<PokeAbility, Long> {

    Optional<PokeAbility> findFirstByNameAndHidden(String name, boolean hidden);

}
