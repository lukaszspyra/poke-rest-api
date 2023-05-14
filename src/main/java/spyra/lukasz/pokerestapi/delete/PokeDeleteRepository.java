package spyra.lukasz.pokerestapi.delete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import javax.validation.constraints.NotNull;

public interface PokeDeleteRepository extends JpaRepository<Pokemon, Long> {

    /**
     * Performs "soft delete" by setting boolean field isDeleted to true
     * Operation is performed only once, when {@link Pokemon#isDeleted()} returns false
     * @param id of pokemon to set as deleted
     * @return number of affected records
     */
    @Modifying
    @Transactional
    @Query("update Pokemon p set p.isDeleted = true where p.id = :id and p.isDeleted = false")
    int softDeleteById(@NotNull Long id);
}
