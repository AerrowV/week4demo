package dat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "activities")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate exerciseDate;
    private String exerciseType;
    private String timeOfDay;
    private int duration;
    private double distance;
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    @JsonIgnore
    private CityInfo city;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "weather_id")
    private WeatherInfo weather;
}