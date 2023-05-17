package spyra.lukasz.pokerestapi.read;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
record PokemonListJson(int count, List<PokemonOverview> results) {
    private static final Logger log = LoggerFactory.getLogger(PokemonListJson.class);

    private record PokemonOverview(String name, String url) {
    }

    List<ProjectedIdAndName> projectionFromJson() {
        log.debug("Mapping PokemonOverviews to ProjectedIdAndNames");
        List<ProjectedIdAndName> projected = new ArrayList<>(results.size());
        return results.stream()
                .map(e -> new ProjectedIdAndName(parseIdFromUrl(e.url), e.name))
                .filter(p -> p.getId() != null)
                .toList();
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
        String[] segments = url.split("/");
        return intInstance.parse(segments[segments.length - 1]).longValue();
    }

    public PokemonListJson {
        log.debug("Create record instance for Pokemon list");
    }
}
