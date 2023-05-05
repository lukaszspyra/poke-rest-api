package spyra.lukasz.pokerestapi.shared;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Holds normal and hidden Abilities
 */
@Entity
@Getter
@Setter
public class PokeAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private boolean isHidden;
}
