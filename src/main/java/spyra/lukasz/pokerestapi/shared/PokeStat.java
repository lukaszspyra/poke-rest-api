package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonProperty("base_stat")
    private int value;

    /**
     * Sets statistics name from nested json node during unmarshalling
     *
     * @param stat
     */
    @JsonSetter("stat")
    public void setNameFromStat(Stat stat) {
        name = stat.getName();
    }
}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Stat {
    private String name;
}
