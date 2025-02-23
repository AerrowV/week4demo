package dat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDTO {

    private LocalDate exerciseDate;
    private String exerciseType;
    private String timeOfDay;
    private int duration;
    private double distance;
    private String comment;
    private WeatherInfoDTO weather;
    private CityInfoDTO city;

}