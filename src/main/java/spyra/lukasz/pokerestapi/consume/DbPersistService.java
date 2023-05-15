package spyra.lukasz.pokerestapi.consume;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spyra.lukasz.pokerestapi.shared.PokeAbility;
import spyra.lukasz.pokerestapi.shared.PokeStat;
import spyra.lukasz.pokerestapi.shared.PokeType;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Persists list if resources into Entities
 */
@RequiredArgsConstructor
@Service
public class DbPersistService {

    private static final Logger log = LoggerFactory.getLogger(DbPersistService.class);

    private final PokeRepository pokeRepository;
    private final PokeAbilityRepository abilityRepository;
    private final PokeStatRepository statRepository;
    private final PokeTypeRepository typeRepository;
    private final JsonParser jsonParser;


    public void initDbFromApi() {
        saveAll(jsonParser.parsePokemonList());
    }

    void saveAll(Collection<Pokemon> pokemons) {
        log.debug("Persisting collection of Pokemon instances");
        pokemons.forEach(this::saveOne);
    }

    @Transactional
    public Pokemon saveOne(Pokemon poke) {
        poke.setAbilities(persistPokeAbilities(poke.getAbilities()));
        poke.setTypes(persistPokeTypes(poke.getTypes()));
        poke.setStats(persistPokeStats(poke.getStats()));
        return pokeRepository.save(poke);
    }

    private Set<PokeStat> persistPokeStats(Collection<PokeStat> stats) {
        return stats
                .stream()
                .map(stat -> statRepository.findFirstByNameAndValue(stat.getName(), stat.getValue()).orElseGet(() -> statRepository.save(stat)))
                .collect(Collectors.toSet());
    }

    private Set<PokeType> persistPokeTypes(Collection<PokeType> types) {
        return types
                .stream()
                .map(type -> typeRepository.findFirstByName(type.getName()).orElseGet(() -> typeRepository.save(type)))
                .collect(Collectors.toSet());
    }

    private Set<PokeAbility> persistPokeAbilities(Collection<PokeAbility> abilities) {
        return abilities
                .stream()
                .map(ability ->
                        abilityRepository.findFirstByNameAndHidden(ability.getName(), ability.isHidden())
                                .orElseGet(() -> abilityRepository.save(ability)))
                .collect(Collectors.toSet());
    }
}
