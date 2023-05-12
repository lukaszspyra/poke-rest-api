package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Holds type of Pokemon
 */
@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeType {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(PokeType.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @NotBlank
    private String name;

    /**
     * Sets ability name from nested json node during unmarshalling
     *
     * @param type
     */
    @JsonSetter("type")
    public void setNameFromTypez(Typez type) {
        log.debug("Setting name from json type node");
        name = type.getName();
    }
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Typez {
    private String name;
}
