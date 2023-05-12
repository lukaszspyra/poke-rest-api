package spyra.lukasz.pokerestapi.create;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import spyra.lukasz.pokerestapi.shared.repository.PokeRepository;

@Component
@RequiredArgsConstructor
class NewPokeIdGenerator {

    private final Long customPokeBeginIndex;
    private final PokeRepository repository;

    Long generate() {
        Long max = repository.getMaxId();
        return max > customPokeBeginIndex ? max + 1 : customPokeBeginIndex + 1;
    }

}
