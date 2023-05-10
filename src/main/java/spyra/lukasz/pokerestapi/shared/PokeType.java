package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Holds type of Pokemon
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    private String name;

    /**
     * Sets ability name from nested json node during unmarshalling
     *
     * @param type
     */
    @JsonSetter("type")
    public void setNameFromTypez(Typez type) {
        name = type.getName();
    }
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Typez {
    private String name;
}
