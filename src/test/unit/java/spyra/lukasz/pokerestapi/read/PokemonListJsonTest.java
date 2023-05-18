package spyra.lukasz.pokerestapi.read;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokemonListJsonTest {

    @ParameterizedTest
    @MethodSource("resourcesIdWithUrl")
    void shallParseIdToLongFromGivenUrl(Long expectedId, String givenUrl) {
        //given
        PokemonListJson json = new PokemonListJson(0, Collections.emptyList());

        //when
        Long actual = json.parseIdFromUrl(givenUrl);

        //then
        assertEquals(expectedId, actual, "Shall parse id from url, but it has not");
    }

    static Stream<Arguments> resourcesIdWithUrl() {
        String resource1 = "https://pokeapi.co/api/v2/pokemon/1/";
        String resource2 = "https://pokeapi.co/api/v2/pokemon/99999/";
        String resource3 = "https://pokeapi.co/api/v2/pokemon/-17/";
        String resource4 = "https://pokeapi.co/api/v2/pokemon/0/";
        return Stream.of(Arguments.of(1L, resource1),
                Arguments.of(99999L, resource2),
                Arguments.of(-17L, resource3),
                Arguments.of(0L, resource4)

        );
    }
}