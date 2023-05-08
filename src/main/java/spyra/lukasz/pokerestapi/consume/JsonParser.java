package spyra.lukasz.pokerestapi.consume;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collection;
import java.util.List;

/**
 * Parses json responses into list of {@link Pokemon}
 */
@Component
@RequiredArgsConstructor
class JsonParser {

    private static final Logger log = LoggerFactory.getLogger(JsonParser.class);

    private final ApiCaller apiCaller;

    List<Pokemon> parsePokemonList() {
        Collection<String> urls = apiCaller.resourceUrlsFromApi();
        log.debug("Start parsing pokemons from resource details list");
        List<Pokemon> pokemons = urls.stream()
                .map(apiCaller::pokemonInstanceFromApi)
                .toList();
        log.debug("Finished parsing pokemons, list with: " + pokemons.size() + " elements");
        return pokemons;
    }

}
