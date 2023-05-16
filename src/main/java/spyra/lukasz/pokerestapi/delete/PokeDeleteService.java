package spyra.lukasz.pokerestapi.delete;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.consume.DbPersistService;
import spyra.lukasz.pokerestapi.expose.ApiExecutor;
import spyra.lukasz.pokerestapi.shared.Pokemon;

@Service
@AllArgsConstructor
class PokeDeleteService {

    private static final Logger log = LoggerFactory.getLogger(PokeDeleteService.class);
    private final PokeDeleteRepository repository;
    private final ApiExecutor executor;
    private final DbPersistService persistService;

    /**
     * Deletes pokemon instance
     *
     * @param id to be searched
     * @return result of delete action
     * @implNote First app db is checked for presence of searched object, if not existing external api is called.
     * Any found result is soft deleted by repository
     * @see ApiExecutor#pokemonInstanceFromApi(Long) if nothing found
     */
    @Caching(evict = {
            @CacheEvict(cacheNames = "SinglePokemonById", key = "#id"),
            @CacheEvict(cacheNames = "ExistingPokemons", allEntries = true)
    })
    boolean deleteOne(Long id) {
        log.debug("Start delete logic, search entity by id in app db");
        if (repository.existsById(id)) {
            log.debug("Resource with given id exists in app db");
            return repository.softDeleteById(id) != 0;
        }
        log.debug("Resource with given id do NOT exists in app db - fetch form api and mark as deleted");
        Pokemon poke = executor.pokemonInstanceFromApi(id);
        poke.setDeleted(true);
        persistService.saveOne(poke);
        return true;
    }
}
