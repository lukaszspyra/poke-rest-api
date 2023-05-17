package spyra.lukasz.pokerestapi.read;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ApiExecutor {
    private static final Logger log = LoggerFactory.getLogger(ApiExecutor.class);
    private final RestOperations restTemplate;
    private final ApiLinkGenerator linkGenerator;


    @Cacheable(cacheNames = "ApiPokemonList", key = "#root.methodName")
    public List<ProjectedIdAndName> pokemonProjectionsFromApiEntryEndpoint() {
        log.debug("Api call for resources in entry url");
        return Objects.requireNonNull(restTemplate.getForObject(linkGenerator.getApiEntryUrl(), PokemonListJson.class)).projectionFromJson();
    }

    /**
     * Finds pokemon in external api
     *
     * @param id to be searched
     * @return instance from api
     * @implNote Relies on {@link org.springframework.web.client.RestTemplate#getForObject(String, Class, Object...)} exception mechanism when not found
     */
    public Pokemon pokemonInstanceFromApi(Long id) {
        log.debug("Api call for single resource details url");
        return restTemplate.getForObject(linkGenerator.generateSingleResourceLink(id), Pokemon.class);
    }

}
