package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.shared.PokeAbility;
import spyra.lukasz.pokerestapi.shared.PokeStat;
import spyra.lukasz.pokerestapi.shared.PokeType;
import spyra.lukasz.pokerestapi.shared.Pokemon;
import spyra.lukasz.pokerestapi.shared.repository.PokeAbilityRepository;
import spyra.lukasz.pokerestapi.shared.repository.PokeRepository;
import spyra.lukasz.pokerestapi.shared.repository.PokeStatRepository;
import spyra.lukasz.pokerestapi.shared.repository.PokeTypeRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class PokeCreateService {

    private static final Logger log = LoggerFactory.getLogger(PokeCreateService.class);
    private final PokeRepository repository;
    private final PokeAbilityRepository abilityRepository;
    private final PokeStatRepository statRepository;
    private final PokeTypeRepository typeRepository;

    /**
     * Saves pokemon with setting relations from db, mitigating detached state of child dependecies (Stats/Abilities/Types)
     *
     * @param poke for persisting
     * @return persisted poke with id set
     */
    @Transactional
    public Pokemon saveOne(Pokemon poke) {
        log.debug("Start setting up all poke required fields in db");
        poke.setAbilities(persistPokeAbilities(poke.getAbilities()));
        poke.setTypes(persistPokeTypes(poke.getTypes()));
        poke.setStats(persistPokeStats(poke.getStats()));
        log.debug("Poke all fields set, sent for persisting");
        return repository.save(poke);
    }

    private Set<PokeStat> persistPokeStats(Collection<PokeStat> stats) {
        log.debug("Getting persisted poke stats");
        return stats
                .stream()
                .map(stat -> statRepository.findFirstByNameAndValue(stat.getName(), stat.getValue()).orElseGet(() -> statRepository.save(stat)))
                .collect(Collectors.toSet());
    }

    private Set<PokeType> persistPokeTypes(Collection<PokeType> types) {
        log.debug("Getting persisted poke types");
        return types
                .stream()
                .map(type -> typeRepository.findFirstByName(type.getName()).orElseGet(() -> typeRepository.save(type)))
                .collect(Collectors.toSet());
    }

    private Set<PokeAbility> persistPokeAbilities(Collection<PokeAbility> abilities) {
        log.debug("Getting persisted poke abilities");
        return abilities
                .stream()
                .map(ability ->
                        abilityRepository.findFirstByNameAndHidden(ability.getName(), ability.isHidden())
                                .orElseGet(() -> abilityRepository.save(ability)))
                .collect(Collectors.toSet());
    }

}
