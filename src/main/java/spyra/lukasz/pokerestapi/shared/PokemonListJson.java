package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PokemonListJson(int count, List<ResourceResult> results) {
    private static final Logger log = LoggerFactory.getLogger(PokemonListJson.class);

    private record ResourceResult(String name, String url) {
    }

    public List<String> resourceUrls() {
        log.debug("Extracting list of resources urls");
        return results.stream().map(ResourceResult::url).collect(Collectors.toList());
    }

    public PokemonListJson {
        log.debug("Create record instance for Pokemon list");
    }
}
