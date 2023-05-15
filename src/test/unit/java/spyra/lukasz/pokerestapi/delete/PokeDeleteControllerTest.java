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
    void shallReturnProperResponseCodeOnDelete(Long idToDelete, boolean deleted, ResponseEntity<Pokemon> response) {
        //given
        PokeDeleteService mockService = Mockito.mock(PokeDeleteService.class);
        PokeDeleteController controller = new PokeDeleteController(mockService);

        //when
        Mockito.when(mockService.deleteOne(idToDelete)).thenReturn(deleted);
        ResponseEntity<Pokemon> delete = controller.delete(idToDelete);

        //then
        assertEquals(response, delete, "Shall return proper response code but it has not");
    }

    private static Stream<Arguments> deleteParameters() {

        return Stream.of(
                Arguments.of(7L, true, ResponseEntity.noContent().build()),
                Arguments.of(7L, false, ResponseEntity.notFound().build()),
                Arguments.of(1000001L, true, ResponseEntity.noContent().build())
        );
    }
}
