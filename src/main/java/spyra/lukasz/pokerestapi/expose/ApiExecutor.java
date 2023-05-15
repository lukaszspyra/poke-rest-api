package spyra.lukasz.pokerestapi.expose;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.Objects;
import java.util.stream.Stream;

@Component
@PropertySource("classpath:settings.properties")
class ApiExecutor {
    private static final Logger log = LoggerFactory.getLogger(ApiExecutor.class);
    private final RestOperations restTemplate;
    private final String apiEntryUrl;

    public ApiExecutor(RestOperations restTemplate, @Value("${api.entry.url}")String apiEntryUrl) {
        this.restTemplate = restTemplate;
        this.apiEntryUrl = apiEntryUrl;
    }

    public Stream<ProjectedIdAndName> pokemonProjectionsFromApiEntryEndpoint() {
        log.debug("Api call for resources in entry url");
        return Objects.requireNonNull(restTemplate.getForObject(apiEntryUrl, PokemonListJson.class)).projectionFromJson();
    }

}
