package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spyra.lukasz.pokerestapi.expose.PokeModelAssembler;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import javax.validation.Valid;


/**
 * Accepts validated new json pokemon resources
 */
@RestController
@RequiredArgsConstructor
class PokeCreateController {

    private static final Logger log = LoggerFactory.getLogger(PokeCreateController.class);
    private final PokeCreateService service;
    private final NewPokeIdGenerator idGenerator;
    private final PokeModelAssembler assembler;

    @PostMapping("/pokemon")
    ResponseEntity<?> newPoke(@Valid @RequestBody Pokemon newPoke) {
        log.debug("Received json for new pokemon resource");
        newPoke.setId(idGenerator.generate());
        EntityModel<Pokemon> entityModel = assembler.toModel(service.saveOne(newPoke));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
