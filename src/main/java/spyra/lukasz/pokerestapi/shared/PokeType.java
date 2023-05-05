package spyra.lukasz.pokerestapi.shared;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Holds type of Pokemon
 */
@Entity
@Getter
@Setter
public class PokeType {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
