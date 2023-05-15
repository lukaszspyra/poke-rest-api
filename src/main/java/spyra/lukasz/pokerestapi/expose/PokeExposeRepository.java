package spyra.lukasz.pokerestapi.expose;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.lang.NonNull;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;
import java.util.Optional;

interface PokeExposeRepository extends JpaRepository<Pokemon, Long> {

    @NonNull
    @Query("select p from Pokemon p join fetch p.abilities join fetch p.stats join fetch p.types where p.id = :id")
    Optional<Pokemon> findAnyById(@NonNull @Param("id") Long id);

    @Query("select p.id from Pokemon p where p.isDeleted = true")
    List<Long> findDeletedIdList();

    List<ProjectedIdAndName> findAllProjectedByIdIsAfterAndIsDeletedFalse(Long id);
}

/**
 * Class based projection of Pokemon entity,
 * it holds only id and name to limit memory usage.
 *
 * @Relation sets the name of embedded node in HAL json.
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "pokemons")
class ProjectedIdAndName extends RepresentationModel<ProjectedIdAndName> {
    @JsonIgnore
    private Long id;
    private String name;
}
