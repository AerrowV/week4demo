package dat.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
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