package spyra.lukasz.pokerestapi.search;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spyra.lukasz.pokerestapi.read.PokeModelAssembler;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final PokeSearchService service;
    private final PokeModelAssembler assembler;

    /**
     * Finds entities by name containing, paginated (default page=0, size=20)
     *
     * @return collection of results transformed to CollectionModel
     */
    @GetMapping("/pokemon/search")
    CollectionModel<EntityModel<ProjectedIdAndName>> searchByNameContaining(@RequestParam
                                                                            @Valid
                                                                            @NotBlank
                                                                            @Size(min = 3, max = 200) String name,
                                                                            Pageable pageable) {
        log.debug("Finding projections by name containing, paginated with page size/number: " + pageable.getPageSize() + "/" + pageable.getPageNumber());
        return assembler.toCollectionModel(service.searchByNameContaining(name, pageable), pageable);
    }

}
