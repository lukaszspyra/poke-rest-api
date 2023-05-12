package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.shared.Pokemon;

@Service
@RequiredArgsConstructor
class PokeCreateService {

    private static final Logger log = LoggerFactory.getLogger(PokeCreateService.class);
    private final PokeCreateRepository repository;

    Pokemon save(Pokemon poke) {
        log.debug("Saving new poke in database");
        return repository.save(poke);
    }

}
