package spyra.lukasz.pokerestapi.search;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class SearchController {

    private static final Logger log = LoggerFactory.getLogger(SearchController.class);
    private final PokeSearchService service;


}
