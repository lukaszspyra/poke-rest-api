package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.consume.DbPersistService;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:settings.properties")
class PokeExposeService {

    private static final Logger log = LoggerFactory.getLogger(PokeExposeService.class);

    private final Long customPokeBeginIndex;
    private final PokeExposeRepository repository;
    private final ApiExecutor executor;
    private final DbPersistService persistService;

    /**
     * Find pokemon by id
     * <p>
     * Searches app db, if not found tires to obtain it from api
     *
     * @param id of pokemon
     * @return found/saved in app db entity
     */
    @Cacheable(cacheNames = "SinglePokemonById")
    public Optional<Pokemon> findById(Long id) {
        log.debug("Searching database for entity with given id");
        Optional<Pokemon> pokeFromDb = repository.findAnyById(id);
        if (pokeFromDb.isPresent()) {
            log.debug("Found entity in app db by id");
            return pokeFromDb.get().isDeleted() ? Optional.empty() : pokeFromDb;
        }
        Pokemon pokeFromApi = persistService.saveOne(executor.pokemonInstanceFromApi(id));
        return Optional.ofNullable(pokeFromApi);
    }

    /**
     * Find all pokemon with status isDeleted==false, projecting it for view list of id and name
     *
     * @param limit  - pagination limit
     * @param offset - start pagination position
     * @return paginated list of resources projections
     */

    @Cacheable(cacheNames = "ExistingPokemons")
    public List<ProjectedIdAndName> findAllProjectedBy(long limit, long offset) {
        log.debug("Searching database for all entities projected by Id and Name");
        Stream<ProjectedIdAndName> apiResources = executor.pokemonProjectionsFromApiEntryEndpoint();
        log.debug("Searching for app db custom entities with id above: " + customPokeBeginIndex);
        Stream<ProjectedIdAndName> ownResources = repository.findAllProjectedByIdIsAfter(customPokeBeginIndex).stream();
        log.debug("Concatenates app db results with poke api results");
        Stream<ProjectedIdAndName> concatResources = Stream.concat(apiResources, ownResources);
        List<Long> deletedIdList = repository.findDeletedIdList();
        log.debug("Removes entities with deleted status from summary list");
        return concatResources
                .filter(parsed -> !deletedIdList.contains(parsed.getId()))
                .skip(offset)
                .limit(limit)
                .toList();
    }
}
