package dat.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temperature;
    private String skyText;
    private String humidity;
    private String windText;
}
