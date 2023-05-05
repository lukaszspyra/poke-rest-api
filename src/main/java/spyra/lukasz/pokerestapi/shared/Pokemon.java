package spyra.lukasz.pokerestapi.shared;

import javax.persistence.*;
import java.util.List;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private long apiId;

    private String name;

    private int height;

    private int weight;

    private String imageUrl;

    @ManyToMany
    private List<PokeAbility> abilities;

    @ManyToOne
    private PokeSpecies species;

    @ManyToMany
    private List<PokeStats> stats;

    @ManyToMany
    private List<PokeType> types;

}
