package spyra.lukasz.pokerestapi.consume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.PokeAbility;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokeAbilityRepositoryIntTest {

    @Autowired
    PokeAbilityRepository underTest;

    @MockBean
    RestOperations restTemplate;

    @BeforeEach
    public void setup() {
        PokeAbility ability1 = new PokeAbility();
        ability1.setName("TestAbility1");
        ability1.setHidden(true);
        PokeAbility ability2 = new PokeAbility();
        ability2.setName("TestAbility2");
        ability2.setHidden(true);
        PokeAbility ability3 = new PokeAbility();
        ability3.setName("TestAbility3");
        PokeAbility ability4 = new PokeAbility();
        ability4.setName("TestAbility3");
        underTest.save(ability1);
        underTest.save(ability2);
        underTest.save(ability3);
        underTest.save(ability4);
    }


    @ParameterizedTest
    @MethodSource("searchParameters")
    void shallFindAbilityByNameAndHidden(String searchName, boolean hidden) {
        //given

        //when
        Optional<PokeAbility> actual = underTest.findFirstByNameAndHidden(searchName, hidden);

        //then
        assertAll("shall find proper entity instance by name and hidden, but it has not",
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(hidden, actual.get().isHidden()),
                () -> assertEquals(searchName, actual.get().getName()),
                () -> assertNotNull(actual.get().getId())
        );

    }

    private static Stream<Arguments> searchParameters() {
        return Stream.of(
                Arguments.of("TestAbility1", true),
                Arguments.of("TestAbility2", true),
                Arguments.of("TestAbility3", false)
        );
    }
}
