package spyra.lukasz.pokerestapi.search;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import spyra.lukasz.pokerestapi.shared.Pokemon;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import javax.validation.constraints.NotBlank;
import java.util.List;

interface PokeSearchRepository extends JpaRepository<Pokemon, Long> {

    @Query("select p.id from Pokemon p where p.isDeleted = true")
    List<Long> findDeletedIdList();

    List<ProjectedIdAndName> findAllProjectedByIdIsAfterAndNameContainingIgnoreCase(@NonNull Long id, @NotBlank String name);

}
