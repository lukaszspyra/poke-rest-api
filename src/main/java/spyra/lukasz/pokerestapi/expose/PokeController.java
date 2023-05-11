package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spyra.lukasz.pokerestapi.shared.Pokemon;

/**
 * Returns resources with hypermedia according to HAL standards (embedded collections of links)
 */
@RestController
@RequiredArgsConstructor
class PokeController {

    private static final Logger log = LoggerFactory.getLogger(PokeController.class);
    private final PokeExposeService service;
    private final PokeModelAssembler assembler;

    @GetMapping("/pokemon/{id}")
    EntityModel<Pokemon> one(@PathVariable Long id) {
        log.debug("Finding one poke by id");
        Pokemon poke = service.findById(id).get();
        return assembler.toModel(poke);
    }

    @GetMapping("/pokemon")
    CollectionModel<EntityModel<ProjectIdAndName>> allProjected() {
        log.debug("Finding all pokemons projected by name and id");
        return assembler.toCollectionModel(service.findAllProjectedBy());
    }

    @PostMapping("/pokemon")
    ResponseEntity<Pokemon> newPoke(@RequestBody Pokemon newPoke) {

        return ResponseEntity.internalServerError().build();
    }

}



