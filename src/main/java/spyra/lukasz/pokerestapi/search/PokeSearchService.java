package spyra.lukasz.pokerestapi.search;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.read.ApiExecutor;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
class PokeSearchService {

    private static final Logger log = LoggerFactory.getLogger(PokeSearchService.class);
    private final PokeSearchRepository repository;
    private final ApiExecutor executor;
    private final Long customPokeBeginIndex;

    /**
     * Search by name containing given name, paginated
     *
     * @param name     to search
     * @param pageable to paginate results
     * @return found projections by name/paginated
     */
    public List<ProjectedIdAndName> searchByNameContaining(String name, Pageable pageable) {
        log.debug("Searching database for all projections, filtered by name");
        Stream<ProjectedIdAndName> apiResourcesByName = executor.pokemonProjectionsFromApiEntryEndpoint()
                .stream()
                .filter(proj -> proj.getName().contains(name));
        log.debug("Searching by name for app db custom entities with id above: " + customPokeBeginIndex);
        Stream<ProjectedIdAndName> ownResourcesByName = repository.findAllProjectedByIdIsAfterAndNameContainingIgnoreCase(customPokeBeginIndex, name).stream();
        log.debug("Concatenates app db results with poke api results");
        Stream<ProjectedIdAndName> concatSearchResults = Stream.concat(apiResourcesByName, ownResourcesByName);
        List<Long> deletedIdList = repository.findDeletedIdList();
        log.debug("Removes entities with deleted status from summary list");
        return concatSearchResults
                .filter(parsed -> !deletedIdList.contains(parsed.getId()))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
    }
}
