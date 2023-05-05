package spyra.lukasz.pokerestapi.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Holds Pokemon's battle statistics resources
 */
@Entity
public class PokeStats {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int value;
}
