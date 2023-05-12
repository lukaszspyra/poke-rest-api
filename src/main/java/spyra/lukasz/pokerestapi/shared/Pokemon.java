package spyra.lukasz.pokerestapi.shared;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(Pokemon.class);

    @Id
    @NotNull
    private Long id;

    @NotBlank
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
    @NotNull
    private Set<PokeAbility> abilities;

    @ManyToMany
    @NotNull
    private Set<PokeStat> stats;

    @ManyToMany
    @NotNull
    private Set<PokeType> types;

}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Sprites {

    @JsonProperty("front_default")
    private String icon;
}
