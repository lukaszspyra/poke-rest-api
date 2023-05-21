package spyra.lukasz.pokerestapi.search;

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
import spyra.lukasz.pokerestapi.shared.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PokeSearchRepositoryIntTest {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private RestOperations restTemplate;

    @Autowired
    private PokeSearchRepository underTest;

    PokeAbility ability1 = initAbility("TestAbility1", true);
    PokeAbility ability2 = initAbility("TestAbility2", false);
    PokeStat stat1 = initStat("Stat1", 1);
    PokeStat stat2 = initStat("Stat2", 2);
    PokeType type1 = initType("Type1");
    PokeType type2 = initType("Type2");

    @BeforeEach
    void setup() {
        Pokemon pokemon1 = new Pokemon(1L, "Pokemon1mon2Pokemon", 1, 11, "Image1",
                Set.of(entityManager.persist(ability1)),
                Set.of(entityManager.persist(stat1)),
                Set.of(entityManager.persist(type1)), false);
        Pokemon pokemon2 = new Pokemon(2L, "Pokemon2", 2, 22, "Image2",
                Set.of(entityManager.persist(ability2)),
                Set.of(entityManager.persist(stat2)),
                Set.of(entityManager.persist(type2)), true);
        Pokemon pokemon3 = new Pokemon(3L, "Pokemon2Pokemon3", 3, 33, "Image3",
                Set.of(entityManager.persist(ability1), entityManager.persist(ability2)),
                Set.of(entityManager.persist(stat1), entityManager.persist(stat2)),
                Set.of(entityManager.persist(type1), entityManager.persist(type2)), true);
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


    @Test
    void shallFindDeletedPokemonsIdList() {
        //given

        //when
        List<Long> actual = underTest.findDeletedIdList();

        //then
        assertEquals(List.of(2L, 3L), actual, "Shall find deleted pokemon's ids, but it has not");
    }

    @ParameterizedTest
    @MethodSource("searchParameters")
    void findAllProjectedByIdIsAfterAndNameContainingIgnoreCase(Long fromId, List<ProjectedIdAndName> expected) {
        //given
        String nameToSearch = "mon2";

        //when
        List<ProjectedIdAndName> actual = underTest.findAllProjectedByIdIsAfterAndNameContainingIgnoreCase(fromId, nameToSearch);

        //then
        assertAll("test",
                () -> assertEquals(expected.size(), actual.size(), String.format("Shall find pokemon's projections with id higher than %d and containing search phrase, but it has not", fromId)),
                () -> assertEquals(expected, actual)
        );
    }

    private static Stream<Arguments> searchParameters() {
        ProjectedIdAndName pr1 = new ProjectedIdAndName(1L, "Pokemon1mon2Pokemon");
        ProjectedIdAndName pr2 = new ProjectedIdAndName(2L, "Pokemon2");
        ProjectedIdAndName pr3 = new ProjectedIdAndName(3L, "Pokemon2Pokemon3");

        return Stream.of(
                Arguments.of(0L, List.of(pr1, pr2, pr3)),
                Arguments.of(1L, List.of(pr2, pr3)),
                Arguments.of(2L, List.of(pr3))
        );
    }
}
