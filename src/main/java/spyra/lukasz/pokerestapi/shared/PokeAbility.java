package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonProperty("is_hidden")
    private boolean isHidden;

    /**
     * Sets ability name from nested json node during unmarshalling
     *
     * @param ability
     */
    @JsonSetter("ability")
    public void setNameFromAbility(Ability ability) {
        this.name = ability.getName();
    }

}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Ability {
    private String name;
}
