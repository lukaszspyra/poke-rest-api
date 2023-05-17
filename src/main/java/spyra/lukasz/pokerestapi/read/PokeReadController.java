package spyra.lukasz.pokerestapi.read;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import spyra.lukasz.pokerestapi.shared.Pokemon;

/**
 * Returns resources with hypermedia according to HAL standards (embedded collections of links)
 */
@RestController
@RequiredArgsConstructor
class PokeReadController {

    private static final Logger log = LoggerFactory.getLogger(PokeReadController.class);
    private final PokeReadService service;
    private final PokeModelAssembler assembler;

    @GetMapping("/pokemon/{id}")
    EntityModel<Pokemon> one(@PathVariable Long id) {
        log.debug("Finding one poke by id or throwing 404");
        Pokemon poke = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        log.debug("Poke found by id, start mapping to EntityModel");
        return assembler.toModel(poke);
    }

    /**
     * Find all entities as entry endpoint, paginated (default page=0, size=20)
     *
     * @return collection of all entities projections transformed to CollectionModel
     */
    @GetMapping("/pokemon")
    CollectionModel<EntityModel<ProjectedIdAndName>> allProjected(Pageable pageable) {
        log.debug("Finding all pokemon's name and id projections, paginated with page size/number: " + pageable.getPageSize() + "/" + pageable.getPageNumber());
        return assembler.toCollectionModel(service.findAllProjectedBy(pageable), pageable);
    }

}
