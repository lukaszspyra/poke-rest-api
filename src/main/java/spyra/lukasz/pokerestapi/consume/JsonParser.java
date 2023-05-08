package spyra.lukasz.pokerestapi.consume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Parses json responses into list of {@link Pokemon}
 */
@Component
@RequiredArgsConstructor
class JsonParser {


}
