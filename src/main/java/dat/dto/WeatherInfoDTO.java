package dat.dto;

import lombok.Data;

@Data
public class WeatherInfoDTO {

    private Double temperature;
    private String skyText;
    private String humidity;
    private String windText;

}