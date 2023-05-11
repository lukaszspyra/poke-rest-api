package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spyra.lukasz.pokerestapi.shared.Pokemon;

@RestController
@RequiredArgsConstructor
class SinglePokeController {

    private static final Logger log = LoggerFactory.getLogger(SinglePokeController.class);
    private final PokeExposeService service;

    @GetMapping("/pokemon/{id}")
    ResponseEntity<Pokemon> one(@PathVariable Long id) {

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pokemon")
    ResponseEntity<Pokemon> newPoke(@RequestBody Pokemon newPoke) {

        return ResponseEntity.internalServerError().build();
    }

}

