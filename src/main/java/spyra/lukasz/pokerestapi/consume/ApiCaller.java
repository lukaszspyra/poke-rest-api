package spyra.lukasz.pokerestapi.consume;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Objects;

/**
 * Fetches data from external properties endpoint into single resources url list
 */
@Component
@PropertySource("classpath:/settings.properties")
class ApiCaller {

    private final String apiUrl;
    private final RestOperations restTemplate;

    public ApiCaller(@Value("${api.url}") String apiUrl, RestOperations restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    List<String> resourceUrlsFromApi() {
        return Objects.requireNonNull(restTemplate.getForObject(apiUrl, PokemonListJson.class)).resourceUrls();
    }

}
