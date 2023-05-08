package spyra.lukasz.pokerestapi.consume;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.List;

/**
 * Persists list if resources into Entities
 */
@RequiredArgsConstructor
@Service
public class DbPersistService {

    private final PokeRepository pokeRepository;

    public List<Pokemon> saveAll(Iterable<Pokemon> pokemons) {
        return pokeRepository.saveAll(pokemons);
    }

}
