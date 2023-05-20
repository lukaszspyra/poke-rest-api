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
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    @JsonIgnore
    private static final Logger log = LoggerFactory.getLogger(Pokemon.class);

    @Id
    private Long id;

    @NotBlank
    private String name;

    @Min(1)
    private int height;

    @Min(1)
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
    @NotEmpty
    @Valid
    private Set<PokeAbility> abilities;

    @ManyToMany
    @NotNull
    @NotEmpty
    @Valid
    private Set<PokeStat> stats;

    @ManyToMany
    @NotNull
    @NotEmpty
    @Valid
    private Set<PokeType> types;

    @JsonIgnore
    private boolean isDeleted;

}

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
class Sprites {

    @JsonProperty("front_default")
    private String icon;
}
