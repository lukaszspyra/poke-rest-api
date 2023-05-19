package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * Holds Pokemon's battle statistics resources
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeStat {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(PokeStat.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @JsonProperty("base_stat")
    private int points;

    /**
     * Sets statistics name from nested json node during unmarshalling
     *
     * @param stat
     */
    @JsonSetter("stat")
    public void setNameFromStat(Stat stat) {
        log.debug("Set name from json stat");
        name = stat.getName();
    }
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Stat {
    private String name;
}
