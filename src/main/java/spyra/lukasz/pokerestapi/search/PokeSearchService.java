package spyra.lukasz.pokerestapi.search;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
class PokeSearchService {

    private static final Logger log = LoggerFactory.getLogger(PokeSearchService.class);
    private final PokeSearchRepository repository;

    List<Pokemon> findByNameContaining(String name){

        return Collections.emptyList();
    }

}
