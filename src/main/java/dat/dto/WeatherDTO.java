package dat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDTO {

    @JsonProperty("LocationName")
    private String locationName;
    @JsonProperty("CurrentData")
    private WeatherInfoDTO currentData;

}
