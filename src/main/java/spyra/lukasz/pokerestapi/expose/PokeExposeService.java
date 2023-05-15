package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
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

    Optional<Pokemon> findById(Long id) {
        log.debug("Searching database for entity with given id");
        return repository.findByIdNotDeleted(id);
    }

    List<ProjectedIdAndName> findAllProjectedBy() {
        log.debug("Searching database for all entities projected by Id and Name");
        Stream<ProjectedIdAndName> apiResources = executor.pokemonProjectionsFromApiEntryEndpoint();
        Stream<ProjectedIdAndName> ownResources = repository.findAllProjectedByIdIsAfterAndIsDeletedFalse(customPokeBeginIndex).stream();
        Stream<ProjectedIdAndName> concatResources = Stream.concat(apiResources, ownResources);
        List<Long> deletedIdList = repository.findDeletedIdList();
        return concatResources
                .filter(parsed -> !deletedIdList.contains(parsed.getId()))
                .toList();
    }
}
