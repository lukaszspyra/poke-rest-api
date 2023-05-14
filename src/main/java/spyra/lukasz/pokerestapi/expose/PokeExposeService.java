package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class PokeExposeService {

    private static final Logger log = LoggerFactory.getLogger(PokeExposeService.class);
    private final PokeExposeRepository repository;

    Optional<Pokemon> findById(Long id) {
        log.debug("Searching database for entity with given id");
        return repository.findByIdNotDeleted(id);
    }

    Collection<ProjectedIdAndName> findAllProjectedBy() {
        log.debug("Searching database for all entities projected by Id and Name");
        return repository.findAllProjectedByAndIsDeletedFalse();
    }
}
