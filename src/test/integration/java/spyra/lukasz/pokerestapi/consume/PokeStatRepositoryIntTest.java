package spyra.lukasz.pokerestapi.consume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.PokeStat;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokeStatRepositoryIntTest {

    @Autowired
    private PokeStatRepository underTest;

    @MockBean
    RestTemplateBuilder templateBuilder;

    @MockBean
    RestOperations restTemplate;

    @BeforeEach
    void setUp() {
        PokeStat stat1 = new PokeStat();
        PokeStat stat2 = new PokeStat();
        PokeStat stat3 = new PokeStat();

        stat1.setName("Stat1");
        stat1.setPoints(1);
        stat2.setName("Stat2");
        stat2.setPoints(2);
        stat3.setName("Stat3");
        stat3.setPoints(3);

        underTest.save(stat1);
        underTest.save(stat2);
        underTest.save(stat3);
    }

    @ParameterizedTest
    @MethodSource("searchStatParameters")
    void findFirstByNameAndPoints(String name, int points) {
        //given

        //when
        Optional<PokeStat> actual = underTest.findFirstByNameAndPoints(name, points);

        //then
        assertAll("Shall find fully instantiated stat by name and points, but it has not",
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(actual.get().getName(), name),
                () -> assertEquals(actual.get().getPoints(), points),
                () -> assertNotNull(actual.get().getId())
        );
    }

    private static Stream<Arguments> searchStatParameters() {
        return Stream.of(
                Arguments.of("Stat1", 1),
                Arguments.of("Stat2", 2),
                Arguments.of("Stat3", 3)
        );
    }
}