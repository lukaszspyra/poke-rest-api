package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spyra.lukasz.pokerestapi.PokeRestApiApplication;

@Component
@RequiredArgsConstructor
class NewPokeIdGenerator {

    private static final Logger log = LoggerFactory.getLogger(NewPokeIdGenerator.class);
    private final Long customPokeBeginIndex;
    private final PokeCreateRepository repository;

    /**
     * Generates id for new custom pokemons.
     * Starts with predefined index in {@link PokeRestApiApplication#customPokeBeginIndex()}, then increments by one.
     *
     * @return
     */
    synchronized Long generate() {
        log.debug("Obtain max id from db");
        Long max = repository.getMaxId();
        log.debug("Increments by one if higher than: " + customPokeBeginIndex);
        return max > customPokeBeginIndex ? max + 1 : customPokeBeginIndex + 1;
    }

}
