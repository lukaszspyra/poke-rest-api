package spyra.lukasz.pokerestapi.read;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;
import spyra.lukasz.pokerestapi.consume.DbPersistService;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokeReadServiceTest {

    @Test
    void findById() {
    }

    @Test
    void findAllProjectedBy() {
    }

    ProjectedIdAndName idAndName1Deleted;
    ProjectedIdAndName idAndName2;
    ProjectedIdAndName idAndName3;
    ProjectedIdAndName idAndName4Deleted;
    ProjectedIdAndName idAndName5;

    @BeforeEach
    void setUp() {
        idAndName1Deleted = new ProjectedIdAndName(1L, "pikachu");
        idAndName2 = new ProjectedIdAndName(2L, "chupikachu");
        idAndName3 = new ProjectedIdAndName(3L, "piiiikachu");
        idAndName4Deleted = new ProjectedIdAndName(4L, "pikapika");
        idAndName5 = new ProjectedIdAndName(5L, "O0pikaCK$u");
    }


    @Test
    void shallReturnNonDeletedPokemonsFromAllSources() {
        //given
        Long mockPokeBeginIndex = 99L;
        PokeExposeRepository mockRepository = Mockito.mock(PokeExposeRepository.class);
        ApiExecutor mockExecutor = Mockito.mock(ApiExecutor.class);
        DbPersistService mockPersistService = Mockito.mock(DbPersistService.class);
        PokeReadService service = new PokeReadService(mockPokeBeginIndex, mockRepository, mockExecutor, mockPersistService);
        List<ProjectedIdAndName> listApi = Arrays.asList(idAndName1Deleted, idAndName2, idAndName3);
        List<ProjectedIdAndName> listDb = Arrays.asList(idAndName4Deleted, idAndName5);


        //when
        Mockito.when(mockExecutor.pokemonProjectionsFromApiEntryEndpoint()).thenReturn(listApi);
        Mockito.when(mockRepository.findAllProjectedByIdIsAfter(mockPokeBeginIndex)).thenReturn(listDb);
        Mockito.when(mockRepository.findDeletedIdList()).thenReturn(List.of(1L, 4L));
        List<ProjectedIdAndName> actual = service.findAllProjectedBy(Pageable.ofSize(10));

        //then
        assertAll("list of non deleted resources",
                () -> assertEquals(3, actual.size(), "Shall return 3 records, but it has not"),
                () -> assertTrue(actual.contains(idAndName2), "Shall contain 'chupikachu', but it has not"),
                () -> assertTrue(actual.contains(idAndName3), "Shall contain 'piiiikachu', but it has not"),
                () -> assertTrue(actual.contains(idAndName5), "Shall contain 'O0pikaCK$u', but it has not")
        );
    }
}
