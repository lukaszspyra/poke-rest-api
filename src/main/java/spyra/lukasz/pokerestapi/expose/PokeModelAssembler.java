package spyra.lukasz.pokerestapi.expose;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
class PokeModelAssembler implements RepresentationModelAssembler<Pokemon, EntityModel<Pokemon>> {

    private static final Logger log = LoggerFactory.getLogger(PokeModelAssembler.class);

    @NonNull
    @Override
    public EntityModel<Pokemon> toModel(@NonNull Pokemon entity) {
        log.debug("Assembling EntityModel of pokemon");
        return EntityModel.of(entity,
                linkTo(methodOn(PokeController.class).one(entity.getId())).withSelfRel(),
                linkTo(methodOn(PokeController.class).allProjected()).withRel("pokemons"));
    }

    @NonNull
    public CollectionModel<EntityModel<ProjectIdAndName>> toCollectionModel(Collection<ProjectIdAndName> entities) {
        log.debug("Assembling EntityModel of pokemon collection");
        List<EntityModel<ProjectIdAndName>> resourcesList = entities
                .stream()
                .map(projectedPoke -> EntityModel.of(projectedPoke,
                        linkTo(methodOn(PokeController.class).one(projectedPoke.getId())).withSelfRel(),
                        linkTo(methodOn(PokeController.class).allProjected()).withRel("pokemons")))
                .toList();
        return CollectionModel.of(resourcesList, linkTo(methodOn(PokeController.class).allProjected()).withSelfRel());
    }
}
