package spyra.lukasz.pokerestapi.consume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;
import java.util.Objects;

/**
 * Fetches data from external properties endpoint into single resources url list
 */
@Component
@PropertySource("classpath:settings.properties")
class ApiCaller {

    private static final Logger log = LoggerFactory.getLogger(ApiCaller.class);

    private final String apiUrl;
    private final RestOperations restTemplate;

    public ApiCaller(@Value("${api.url}") String apiUrl, RestOperations restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    List<String> resourceUrlsFromApi() {
        log.debug("Api call for properties defined entry url");
        return Objects.requireNonNull(restTemplate.getForObject(apiUrl, PokemonListJson.class)).resourceUrls();
    }

    Pokemon pokemonInstanceFromApi(String singlePokemonUrl) {
        log.debug("Api call for single resource details url");
        return restTemplate.getForObject(singlePokemonUrl, Pokemon.class);
    }
}
