package spyra.lukasz.pokerestapi.consume;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PokemonListJsonParseTest {

    private final RestOperations restTemplate = new RestTemplate();
    private final String pokeListUrl = "https://pokeapi.co/api/v2/pokemon?limit=10&offset=0";

    @Test
    void shallCreateDTOFromApiWithLimitOf10Entries() {
        //given

        //when
        ResponseEntity<PokemonListJson> response = restTemplate.getForEntity(pokeListUrl, PokemonListJson.class);

        //then
        assertAll("poke list",
                () -> assertEquals(1281, Objects.requireNonNull(response.getBody()).count(), "Shall return 1281 available pokemons, but it has not"),
                () -> assertEquals(10, Objects.requireNonNull(response.getBody()).results().size(), "Shall return 10 records, but it has not"),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode(), "Shall return status OK 200, but it has not"));
    }

}
