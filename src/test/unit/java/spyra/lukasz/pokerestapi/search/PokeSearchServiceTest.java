package spyra.lukasz.pokerestapi.search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import spyra.lukasz.pokerestapi.read.ApiExecutor;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokeSearchServiceTest {

    ProjectedIdAndName idAndName1;
    ProjectedIdAndName idAndName2;
    ProjectedIdAndName idAndName3;
    ProjectedIdAndName idAndName4;
    ProjectedIdAndName idAndName5;

    @BeforeEach
    void setUp() {
        idAndName1 = new ProjectedIdAndName(1L, "pikachu");
        idAndName2 = new ProjectedIdAndName(2L, "chupikachu");
        idAndName3 = new ProjectedIdAndName(3L, "piiiikachu");
        idAndName4 = new ProjectedIdAndName(4L, "pikapika");
        idAndName5 = new ProjectedIdAndName(5L, "O0pikaCK$u");
    }


    @Test
    void searchByNamePikaShallIgnoreCaseAndReturn3Records() {
        //given
        PokeSearchRepository mockRepository = Mockito.mock(PokeSearchRepository.class);
        ApiExecutor mockExecutor = Mockito.mock(ApiExecutor.class);
        Long mockCustomPokeIndex = 9999L;
        String name = "Pika";
        List<ProjectedIdAndName> listApi = Arrays.asList(idAndName1, idAndName2, idAndName3);
        List<ProjectedIdAndName> listDb = Arrays.asList(idAndName4, idAndName5);
        PokeSearchService service = new PokeSearchService(mockRepository, mockExecutor, mockCustomPokeIndex);


        //when
        Mockito.when(mockRepository.findAllProjectedByIdIsAfterAndNameContainingIgnoreCase(mockCustomPokeIndex, name)).thenReturn(listDb);
        Mockito.when(mockExecutor.pokemonProjectionsFromApiEntryEndpoint()).thenReturn(listApi);
        Mockito.when(mockRepository.findDeletedIdList()).thenReturn(List.of(1L));
        List<ProjectedIdAndName> actual = service.searchByNameContaining(name, Pageable.ofSize(10));

        //then
        assertAll("search results",
                () -> assertEquals(3, actual.size(), "Shall return 3 records, but it has not"),
                () -> assertTrue(actual.contains(idAndName2), "Shall contain 'chupikachu', but it has not"),
                () -> assertTrue(actual.contains(idAndName4), "Shall contain 'pikapika', but it has not"),
                () -> assertTrue(actual.contains(idAndName5), "Shall contain 'O0pikaCK$u', but it has not")
        );
    }
}