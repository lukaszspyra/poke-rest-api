package spyra.lukasz.pokerestapi.delete;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PokeDeleteControllerTest {

    @ParameterizedTest
    @MethodSource("deleteParameters")
    void shallReturnProperResponseCodeOnDelete(Long idToDelete, int deletedCount, ResponseEntity<Pokemon> response) {
        //given
        PokeDeleteService mockService = Mockito.mock(PokeDeleteService.class);
        PokeDeleteController controller = new PokeDeleteController(mockService);

        //when
        Mockito.when(mockService.deleteOne(idToDelete)).thenReturn(deletedCount);
        ResponseEntity<Pokemon> delete = controller.delete(idToDelete);

        //then
        assertEquals(response, delete, "Shall return proper response code but it has not");
    }

    private static Stream<Arguments> deleteParameters() {

        return Stream.of(
                Arguments.of(7L, 1, ResponseEntity.noContent().build()),
                Arguments.of(7L, 0, ResponseEntity.notFound().build()),
                Arguments.of(1000001L, 10, ResponseEntity.noContent().build())
        );
    }
}
