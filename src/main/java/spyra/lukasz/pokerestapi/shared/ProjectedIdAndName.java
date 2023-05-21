package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

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
public class ProjectedIdAndName extends RepresentationModel<ProjectedIdAndName> {
    @JsonIgnore
    private Long id;
    private String name;
}
