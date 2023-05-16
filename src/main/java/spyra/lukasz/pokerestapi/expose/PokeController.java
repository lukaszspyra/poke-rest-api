package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import spyra.lukasz.pokerestapi.shared.Pokemon;

/**
 * Returns resources with hypermedia according to HAL standards (embedded collections of links)
 */
@RestController
@RequiredArgsConstructor
@PropertySource("classpath:settings.properties")
class PokeController {

    private static final Logger log = LoggerFactory.getLogger(PokeController.class);
    private final PokeExposeService service;
    private final PokeModelAssembler assembler;

    @GetMapping("/pokemon/{id}")
    EntityModel<Pokemon> one(@PathVariable Long id) {
        log.debug("Finding one poke by id or throwing 404");
        Pokemon poke = service.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "resource not found"));
        log.debug("Poke found by id, start mapping to EntityModel");
        return assembler.toModel(poke);
    }

    /**
     * Find all entities as entry endpoint
     *
     * @return collection of all entities projections transformed to CollectionModel
     */
    @GetMapping("/pokemon")
    CollectionModel<EntityModel<ProjectedIdAndName>> allProjected(@RequestParam(name = "limit", defaultValue = "${default.page.offset}") long limit,
                                                                  @RequestParam(name = "offset", defaultValue = "${default.page.limit}") long offset) {
        log.debug("Finding all pokemon's name and id projections, paginated with params - limit:" + limit + "  offset: " + offset);
        return assembler.toCollectionModel(service.findAllProjectedBy(limit, offset));
    }

}
