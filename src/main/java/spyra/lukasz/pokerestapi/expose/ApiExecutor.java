package spyra.lukasz.pokerestapi.expose;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Objects;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@PropertySource("classpath:settings.properties")
class ApiExecutor {
    private static final Logger log = LoggerFactory.getLogger(ApiExecutor.class);
    private final RestOperations restTemplate;
    private final ApiLinkGenerator linkGenerator;


    public Stream<ProjectedIdAndName> pokemonProjectionsFromApiEntryEndpoint() {
        log.debug("Api call for resources in entry url");
        return Objects.requireNonNull(restTemplate.getForObject(linkGenerator.getApiEntryUrl(), PokemonListJson.class)).projectionFromJson();
    }

    Pokemon pokemonInstanceFromApi(Long id) {
        log.debug("Api call for single resource details url");
        return restTemplate.getForObject(linkGenerator.generateSingleResourceLink(id), Pokemon.class);
    }

}
