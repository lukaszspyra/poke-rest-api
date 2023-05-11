package spyra.lukasz.pokerestapi.expose;

import org.springframework.data.jpa.repository.JpaRepository;
import spyra.lukasz.pokerestapi.shared.Pokemon;

import java.util.Collection;

interface PokeExposeRepository extends JpaRepository<Pokemon, Long> {

    Collection<ProjectIdAndName> findAllProjectedBy();
}

interface ProjectIdAndName {
    Long getId();

    String getName();
}
