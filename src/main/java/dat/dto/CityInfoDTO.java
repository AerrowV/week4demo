package dat.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityInfoDTO {

//    private String id;
//    private String hovedtype;
//    private String undertype;

    private String primærtnavn;

//    private String primærnavnestatus;

//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
//    private Instant ændret;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
//    private Instant geo_ændret;

//    private int geo_version;
//    private String href;

    private PropertiesDTO egenskaber;
    private List<Double> visueltcenter;

//    private List<Double> bbox;

    private List<CommuneDTO> kommuner;

//    private List<String> sekundærenavne;

}
