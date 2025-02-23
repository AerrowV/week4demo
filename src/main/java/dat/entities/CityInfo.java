package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String geocoordinates;
    private String municipality;
    private int population;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Activity> activities;
}
