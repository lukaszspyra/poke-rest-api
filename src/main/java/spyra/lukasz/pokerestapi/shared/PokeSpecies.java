package spyra.lukasz.pokerestapi.shared;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Holds species relevant features
 */
@Entity
@Getter
@Setter
public class PokeSpecies {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int genderRate;

    private int captureRate;

    private String habitat;

    private String yellow;

}
