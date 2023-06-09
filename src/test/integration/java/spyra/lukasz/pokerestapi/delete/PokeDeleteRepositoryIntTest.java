package spyra.lukasz.pokerestapi.delete;

import org.junit.jupiter.api.BeforeEach;
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

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PokeDeleteRepositoryIntTest {

    @Autowired
    private PokeDeleteRepository underTest;

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

    @BeforeEach
    void setup() {
        Pokemon pokemon1 = new Pokemon(1L, "Pokemon1", 1, 11, "Image1",
                Set.of(entityManager.persist(ability1)),
                Set.of(entityManager.persist(stat1)),
                Set.of(entityManager.persist(type1)), false);
        Pokemon pokemon2 = new Pokemon(2L, "Pokemon2", 2, 22, "Image2",
                Set.of(entityManager.persist(ability2)),
                Set.of(entityManager.persist(stat2)),
                Set.of(entityManager.persist(type2)), true);
        Pokemon pokemon3 = new Pokemon(3L, "Pokemon3", 3, 33, "Image3",
                Set.of(entityManager.persist(ability1), entityManager.persist(ability2)),
                Set.of(entityManager.persist(stat1), entityManager.persist(stat2)),
                Set.of(entityManager.persist(type1), entityManager.persist(type2)), false);
        entityManager.persist(pokemon1);
        entityManager.persist(pokemon2);
        entityManager.persist(pokemon3);
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

    @ParameterizedTest
    @MethodSource("idParameters")
    void shallSoftDeletePokemonsById(Long id, int expected) {
        //given

        //when
        int recordsDeleted = underTest.softDeleteById(id);

        //then
        assertEquals(expected, recordsDeleted, "Shall modify proper number of records, but it has not");
    }

    private static Stream<Arguments> idParameters() {
        return Stream.of(
                Arguments.of(1L, 1),
                Arguments.of(2L, 0),
                Arguments.of(3L, 1)
        );
    }
}
