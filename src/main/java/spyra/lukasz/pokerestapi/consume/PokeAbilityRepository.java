package spyra.lukasz.pokerestapi.consume;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.PokeAbility;

interface PokeAbilityRepository extends JpaRepository<PokeAbility, Long> {
}
