package spyra.lukasz.pokerestapi.delete;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spyra.lukasz.pokerestapi.shared.Pokemon;


/**
 * Handles pokemon delete functionality
 */
@RestController
@RequiredArgsConstructor
class PokeDeleteController {

    private static final Logger log = LoggerFactory.getLogger(PokeDeleteController.class);
    private final PokeDeleteService service;

    @DeleteMapping("/pokemon/{id}")
    ResponseEntity<Pokemon> delete(@PathVariable Long id) {
        log.debug("Deletes pokemon by setting field isDeleted=true");
        if (service.deleteOne(id) == 0) {
            log.debug("Pokemon's id " + id + " not found - delete cancelled");
            return ResponseEntity.notFound().build();
        }
        log.debug("Pokemon's id " + id + " found - delete successful");
        return ResponseEntity.noContent().build();
    }

}
