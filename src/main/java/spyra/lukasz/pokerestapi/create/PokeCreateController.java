package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spyra.lukasz.pokerestapi.shared.Pokemon;

/**
 * Accepts new json resources
 */
@RestController
@RequiredArgsConstructor
class PokeCreateController {

    private static final Logger log = LoggerFactory.getLogger(PokeCreateController.class);
    private final PokeCreateService service;

    @PostMapping("/pokemon")
    Pokemon newPoke(@Validated @RequestBody Pokemon newPoke) {
        log.debug("Received json for new pokemon resource");
        Pokemon draft = newPoke;

        return service.save(draft);
    }

}
