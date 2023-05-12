package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import javax.validation.Valid;


/**
 * Accepts validated new json pokemon resources
 */
@RestController
@RequiredArgsConstructor
class PokeCreateController {

    private static final Logger log = LoggerFactory.getLogger(PokeCreateController.class);
    private final PokeCreateService service;
    private final NewPokeIdGenerator idGenerator;

    @PostMapping("/pokemon")
    Pokemon newPoke(@Valid @RequestBody Pokemon newPoke) {
        log.debug("Received json for new pokemon resource");
        newPoke.setId(idGenerator.generate());
        return service.save(newPoke);
    }

}
