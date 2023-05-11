package spyra.lukasz.pokerestapi.consume;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import spyra.lukasz.pokerestapi.shared.PokeAbility;
import spyra.lukasz.pokerestapi.shared.PokeStat;
import spyra.lukasz.pokerestapi.shared.PokeType;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PokemonJsonParseTest {
    private final RestOperations restTemplate = new RestTemplate();
    private final String pokeUrl = "https://pokeapi.co/api/v2/pokemon/pikachu";

    @Test
    void shallCreateDomainDirectlyFromApi() {
        //given
        final List<String> statNames = List.of("hp", "attack", "defense", "special-attack", "special-defense", "speed");
        final String pokeName = "pikachu";
        final String pokeType = "electric";
        final int apiId = 25;
        final int height = 4;
        final int weight = 60;

        //when
        ResponseEntity<Pokemon> response = restTemplate.getForEntity(pokeUrl, Pokemon.class);
        Pokemon responseBody = Objects.requireNonNull(response.getBody());

        //then
        assertAll("single poke",
                () -> assertEquals(apiId, responseBody.getApiId(), "Shall return proper API ID, but it has not"),
                () -> assertEquals(pokeName, responseBody.getName(), "Shall return proper name, but it has not"),
                () -> assertEquals(height, responseBody.getHeight(), "Shall return proper height, but it has not"),
                () -> assertEquals(weight, responseBody.getWeight(), "Shall return proper weight, but it has not"),
                () -> assertNotNull(responseBody.getImageUrl(), "Shall return image url, but it has not"),
                () -> assertEquals(2, responseBody.getAbilities().size(), "Shall return proper abilities count, but it has not"),
                () -> assertEquals(2, responseBody.getAbilities()
                        .stream()
                        .map(PokeAbility::getName)
                        .filter(name -> "static".equals(name) || "lightning-rod".equals(name))
                        .count(), "Shall return proper abilities names, but it has not"),
                () -> assertEquals(6, responseBody.getStats().size(), "Shall return proper stats count, but it has not"),
                () -> assertTrue(responseBody.getStats()
                        .stream()
                        .map(PokeStat::getName)
                        .toList()
                        .containsAll(statNames), "Shall return proper stats names, but it has not"),
                () -> assertEquals(1, responseBody.getTypes().size(), "Shall return proper types count, but it has not"),
                () -> assertTrue(responseBody.getTypes()
                        .stream()
                        .map(PokeType::getName)
                        .toList().contains(pokeType), "Shall return proper types names, but it has not"),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode(), "Shall return status OK 200, but it has not"));
    }
}
