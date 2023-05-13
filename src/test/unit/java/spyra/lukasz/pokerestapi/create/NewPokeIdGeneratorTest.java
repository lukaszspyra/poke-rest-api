package spyra.lukasz.pokerestapi.create;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @ParameterizedTest by Junit5 for generating correct id numbers
 */
class NewPokeIdGeneratorTest {

    private final PokeCreateRepository mockRepository = Mockito.mock(PokeCreateRepository.class);

    @ParameterizedTest
    @MethodSource("maxIdFromDbWithExpectedGeneratedId")
    void shallGenerateProperIdForGivenDBCountAndCustomIndexStartingFrom100Exclusively(Long maxIdValueFromDb, Long expectedId) {
        //given
        NewPokeIdGenerator generator = new NewPokeIdGenerator(100L, mockRepository);
        Mockito.when(mockRepository.getMaxId()).thenReturn(maxIdValueFromDb);

        //when
        Long generatedId = generator.generate();

        //then
        assertEquals(expectedId, generatedId, "Shall generate proper id, but it has not");
    }

    private static Stream<Arguments> maxIdFromDbWithExpectedGeneratedId() {
        return Stream.of(
                Arguments.of(-1L, 101L),
                Arguments.of(0L, 101L),
                Arguments.of(100L, 101L),
                Arguments.of(101L, 102L),
                Arguments.of(10000010L, 10000011L)
        );
    }
}
