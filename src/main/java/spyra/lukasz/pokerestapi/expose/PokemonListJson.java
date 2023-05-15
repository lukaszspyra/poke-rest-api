package spyra.lukasz.pokerestapi.expose;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
record PokemonListJson(int count, List<PokemonOverview> results) {
    private static final Logger log = LoggerFactory.getLogger(PokemonListJson.class);

    private record PokemonOverview(String name, String url) {
    }

    Stream<ProjectedIdAndName> projectionFromJson() {
        log.debug("Mapping PokemonOverviews to ProjectedIdAndNames");
        List<ProjectedIdAndName> projected = new ArrayList<>(results.size());
        return results.stream()
                .map(e -> new ProjectedIdAndName(parseIdFromUrl(e.url), e.name))
                .filter(p -> p.getId() != null);
    }

    private Long parseIdFromUrl(String url) {
        log.debug("Start parsing id from url");
        NumberFormat intInstance = NumberFormat.getIntegerInstance();
        Long parsed = null;
        try {
            parsed = parse(intInstance, url);
            log.debug("Success during parsing url: " + url);
        } catch (ParseException e) {
            log.debug("Skipped - Exception during parsing pokemon with url: " + url);
        }
        return parsed;
    }

    /**
     * Parses last letter from given url
     *
     * @param intInstance - for integer rounding
     * @param url         - for parsing
     * @return parsed number
     * @throws ParseException if not successful parsing (no parseable digit as the last character)
     */
    private long parse(NumberFormat intInstance, String url) throws ParseException {
        return intInstance.parse(url.substring(url.length() - 1)).longValue();
    }

    public PokemonListJson {
        log.debug("Create record instance for Pokemon list");
    }
}
