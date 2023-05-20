package spyra.lukasz.pokerestapi.read;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.PokeAbility;
import spyra.lukasz.pokerestapi.shared.PokeStat;
import spyra.lukasz.pokerestapi.shared.PokeType;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokeExposeRepositoryIntTest {

    @Autowired
    private PokeExposeRepository underTest;

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private RestOperations restTemplate;

    PokeAbility ability1 = initAbility("TestAbility1", true);
    PokeAbility ability2 = initAbility("TestAbility2", false);
    PokeStat stat1 = initStat("Stat1", 1);
    PokeStat stat2 = initStat("Stat2", 2);
    PokeType type1 = initType("Type1");
    PokeType type2 = initType("Type2");
    Pokemon pokemon1;
    Pokemon pokemon2;
    Pokemon pokemon3;

    Pokemon[] pokemons;

    @Test
    void shallFindDeletedPokemonIds() {
        //given
        List<Long> deletedIds = Arrays.stream(pokemons).filter(Pokemon::isDeleted).map(Pokemon::getId).toList();

        //when
        List<Long> actual = underTest.findDeletedIdList();

        //then
        assertEquals(deletedIds, actual, "Shall find deleted pokemons, bt it has not");
    }

    @Test
    void findAllProjectedByIdIsAfter() {
    }

    @BeforeEach
    void setup() {
        pokemon1 = new Pokemon(1L, "Pokemon1", 1, 11, "Image1",
                Set.of(entityManager.persist(ability1)),
                Set.of(entityManager.persist(stat1)),
                Set.of(entityManager.persist(type1)), false);
        pokemon2 = new Pokemon(2L, "Pokemon2", 2, 22, "Image2",
                Set.of(entityManager.persist(ability2)),
                Set.of(entityManager.persist(stat2)),
                Set.of(entityManager.persist(type2)), true);
        pokemon3 = new Pokemon(3L, "Pokemon3", 3, 33, "Image3",
                Set.of(entityManager.persist(ability1), entityManager.persist(ability2)),
                Set.of(entityManager.persist(stat1), entityManager.persist(stat2)),
                Set.of(entityManager.persist(type1), entityManager.persist(type2)), false);
        pokemons = new Pokemon[]{entityManager.persist(pokemon1), entityManager.persist(pokemon2), entityManager.persist(pokemon3)};

    }


    @ParameterizedTest
    @MethodSource("searchIdParameters")
    void shallFindPokemonById(Long id) {
        //given

        //when
        Optional<Pokemon> actual = underTest.findAnyById(id);

        //then
        assertAll("shall find pokemon instance by id proper, but it has not",
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(pokemons[id.intValue() - 1].getId(), actual.get().getId()),
                () -> assertEquals(pokemons[id.intValue() - 1].getHeight(), actual.get().getHeight()),
                () -> assertEquals(pokemons[id.intValue() - 1].getWeight(), actual.get().getWeight()),
                () -> assertEquals(pokemons[id.intValue() - 1].getImageUrl(), actual.get().getImageUrl()),
                () -> assertEquals(pokemons[id.intValue() - 1].getAbilities(), actual.get().getAbilities()),
                () -> assertEquals(pokemons[id.intValue() - 1].getStats(), actual.get().getStats()),
                () -> assertEquals(pokemons[id.intValue() - 1].getTypes(), actual.get().getTypes()),
                () -> assertEquals(pokemons[id.intValue() - 1].isDeleted(), actual.get().isDeleted())
        );
    }

    private static Stream<Arguments> searchIdParameters() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L)
        );
    }

    private PokeType initType(String name) {
        PokeType type = new PokeType();
        type.setName(name);
        return type;
    }

    private PokeStat initStat(String name, int points) {
        PokeStat pokeStat = new PokeStat();
        pokeStat.setName(name);
        pokeStat.setPoints(points);
        return pokeStat;
    }

    private PokeAbility initAbility(String name, boolean hidden) {
        PokeAbility ability = new PokeAbility();
        ability.setName(name);
        ability.setHidden(hidden);
        return ability;
    }
}
