package spyra.lukasz.pokerestapi.delete;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class PokeDeleteService {

    private static final Logger log = LoggerFactory.getLogger(PokeDeleteService.class);
    private PokeDeleteRepository repository;

    int deleteOne(Long id) {
        log.debug("Delegates delete process to repository");
        return repository.softDeleteById(id);
    }
}
