package spyra.lukasz.pokerestapi.shared;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
class ApiExecutor {
    private static final Logger log = LoggerFactory.getLogger(ApiExecutor.class);
    private final RestOperations restTemplate;

    List<String> resourceUrlsFromApiRootEndpoint(String apiRootUrl) {
        log.debug("Api call for properties defined entry url");
        return Objects.requireNonNull(restTemplate.getForObject(apiRootUrl, PokemonListJson.class)).resourceUrls();
    }

}
