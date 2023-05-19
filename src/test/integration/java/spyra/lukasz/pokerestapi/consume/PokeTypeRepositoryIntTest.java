package spyra.lukasz.pokerestapi.consume;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestOperations;
import spyra.lukasz.pokerestapi.shared.PokeType;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokeTypeRepositoryIntTest {

    @Autowired
    private PokeTypeRepository underTest;

    @MockBean
    RestOperations restTemplate;

    @BeforeEach
    void setUp() {
        PokeType type1 = new PokeType();
        PokeType type2 = new PokeType();
        PokeType type3 = new PokeType();

        type1.setName("Type1");
        type2.setName("Type2");
        type3.setName("Type3");

        underTest.save(type1);
        underTest.save(type2);
        underTest.save(type3);
    }

    @ParameterizedTest
    @MethodSource("searchTypeParameters")
    void findPokeTypeByName(String name) {
        //given

        //when
        Optional<PokeType> actual = underTest.findFirstByName(name);

        //then
        assertAll("Shall find fully instantiated type by name, but it has not",
                () -> assertTrue(actual.isPresent()),
                () -> assertEquals(actual.get().getName(), name),
                () -> assertNotNull(actual.get().getId())
        );
    }

    private static Stream<Arguments> searchTypeParameters() {
        return Stream.of(
                Arguments.of("Type1"),
                Arguments.of("Type2"),
                Arguments.of("Type3")
        );
    }
}
