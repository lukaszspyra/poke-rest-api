package spyra.lukasz.pokerestapi.delete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import javax.validation.constraints.NotNull;

public interface PokeDeleteRepository extends JpaRepository<Pokemon, Long> {

    /**
     * Performs "soft delete" by setting boolean field isDeleted to true
     * @param id of pokemon to set as deleted
     * @return number of affected records
     */
    @Query("update Pokemon p set p.isDeleted = true where p.id = :id")
    int softDeleteById(@NotNull Long id);
}
