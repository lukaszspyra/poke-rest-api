package spyra.lukasz.pokerestapi.search;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.read.ApiExecutor;
import spyra.lukasz.pokerestapi.shared.Pokemon;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
class PokeSearchService {

    private static final Logger log = LoggerFactory.getLogger(PokeSearchService.class);
    private final PokeSearchRepository repository;
    private final ApiExecutor executor;

    List<Pokemon> findByNameContaining(String name, Pageable pageable) {
        log.debug("Searching database for all entities projected by Id and Name");
        Stream<ProjectedIdAndName> apiResources = executor.pokemonProjectionsFromApiEntryEndpoint().stream();

        return Collections.emptyList();
    }

}
