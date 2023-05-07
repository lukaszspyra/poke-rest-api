package spyra.lukasz.pokerestapi.consume;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
record PokemonListJson(int count, List<ResourceResult> results) {
    private static final Logger log = LoggerFactory.getLogger(PokemonListJson.class);

    private record ResourceResult(String name, String url) {
    }

    List<String> resourceUrls() {
        return results.stream().map(ResourceResult::url).collect(Collectors.toList());
    }

    PokemonListJson {
        log.debug("Create record instance for Pokemon list");
    }
}
