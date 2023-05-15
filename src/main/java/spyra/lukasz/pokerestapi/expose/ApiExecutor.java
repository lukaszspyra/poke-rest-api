package spyra.lukasz.pokerestapi.expose;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public
class ApiExecutor {
    private static final Logger log = LoggerFactory.getLogger(ApiExecutor.class);
    private final RestOperations restTemplate;

    public Stream<ProjectedIdAndName> pokemonProjectionsFromApiEntryEndpoint(String apiRootUrl) {
        log.debug("Api call for resources in entry url");
        return Objects.requireNonNull(restTemplate.getForObject(apiRootUrl, PokemonListJson.class)).projectionFromJson();
    }

}
