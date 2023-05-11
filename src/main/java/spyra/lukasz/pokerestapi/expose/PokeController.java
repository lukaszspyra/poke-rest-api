package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
class PokeController {

    private static final Logger log = LoggerFactory.getLogger(PokeController.class);
    private final PokeExposeService service;

    @GetMapping("/pokemon/{id}")
    EntityModel<Pokemon> one(@PathVariable Long id) {
        Pokemon poke = service.findById(id).get();
        return EntityModel.of(poke,
                linkTo(methodOn(PokeController.class).one(id)).withSelfRel());
    }

    @PostMapping("/pokemon")
    ResponseEntity<Pokemon> newPoke(@RequestBody Pokemon newPoke) {

        return ResponseEntity.internalServerError().build();
    }

}

