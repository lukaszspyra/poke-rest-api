package spyra.lukasz.pokerestapi.consume;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonParserTest {

    @Test
    void parsePokemonList() {
        //given
        ApiCaller apiCaller = new ApiCaller("https://pokeapi.co/api/v2/pokemon?limit=10&offset=0", new RestTemplate());
        JsonParser jsonParser = new JsonParser(apiCaller);

        //when
        List<Pokemon> pokemons = jsonParser.parsePokemonList();

        //then
        assertEquals(10, pokemons.size(), "Shall parse proper number of pokemons, but it has not");
    }
}
