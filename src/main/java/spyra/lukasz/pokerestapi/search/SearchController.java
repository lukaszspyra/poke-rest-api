package spyra.lukasz.pokerestapi.search;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import spyra.lukasz.pokerestapi.read.PokeModelAssembler;
import spyra.lukasz.pokerestapi.shared.ProjectedIdAndName;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
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
    CollectionModel<EntityModel<ProjectedIdAndName>> searchByNameContaining(@NotBlank
                                                                            @Size(min = 3, max = 200)
                                                                            @RequestParam
                                                                            String name,
                                                                            Pageable pageable) {
        log.debug(String.format("Finding projections by name containing %s, paginated with page size: %d, number: %d ", name, pageable.getPageSize(), pageable.getPageNumber()));
        return assembler.toCollectionModel(service.searchByNameContaining(name, pageable), pageable);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    private ResponseEntity<Map<String, HttpStatus>> handleRequestParamError(final ConstraintViolationException exception) {
        log.error(exception.getMessage(), exception);
        Map<String, HttpStatus> errors = new HashMap<>();
        errors.put(exception.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
