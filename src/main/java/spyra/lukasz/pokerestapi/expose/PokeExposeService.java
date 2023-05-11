package spyra.lukasz.pokerestapi.expose;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class PokeExposeService {

    private static final Logger log = LoggerFactory.getLogger(PokeExposeService.class);
    private final PokeExposeRepository repository;

}
