package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(Pokemon.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @JsonProperty("id")
    private long apiId;

    private String name;

    private int height;

    private int weight;

    //Todo: find another API with high-res pictures
    private String imageUrl;

    @JsonSetter("sprites")
    public void imageUrlFromNestedJson(Sprites sprites) {
        log.debug("Setting image url from Json Sprites node");
        imageUrl = sprites.getIcon();
    }

    @ManyToMany
    private List<PokeAbility> abilities;

    @ManyToMany
    private List<PokeStat> stats;

    @ManyToMany
    private List<PokeType> types;

}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Sprites {

    @JsonProperty("front_default")
    private String icon;
}
