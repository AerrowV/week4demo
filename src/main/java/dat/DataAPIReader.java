package dat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;

public class DataAPIReader {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        DataAPIReader reader = new DataAPIReader();
        String city = "Lillerød";
//        String result = reader.getDataFromURL("https://vejr.eu/api.php?location=%%&degree=C", city);
//        String cityResult = reader.getDataFromURL("https://dawa.aws.dk/steder?hovedtype=Bebyggelse&undertype=by&prim%C3%A6rtnavn=%%", city);
//        WeatherDTO weatherDTO = reader.getWeatherDTO(result);
//        StedDto stedDto = reader.getCityData(cityResult);
//        System.out.println(result);
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(weatherDTO);
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(cityResult);
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(stedDto);

    }

    public WeatherDTO getWeatherDTO(String json) {
        try {
            WeatherDTO weatherDTO = objectMapper.readValue(json, WeatherDTO.class);
            return weatherDTO;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public StedDto getCityData(String json) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            StedDto stedDto = objectMapper.readValue(json, StedDto[].class)[0];
            return stedDto;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getDataFromURL(String url) {
//        String cityURL = url.replace("%%", city);
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response.statusCode() == 200) {
                String body = response.body();
//                System.out.println(response.body());
                return body;
//                WeatherDTO weatherDTO = objectMapper.readValue(body, WeatherDTO.class);
//                System.out.println(weatherDTO);
//                System.out.println(weatherDTO.locationName);

            } else {
                System.out.println("GET request failed. Status code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Data
    private static class WeatherDTO {
        @JsonProperty("LocationName")
        private String locationName;
        @JsonProperty("CurrentData")
        private CurrentData currentData;

    }

    @Data
    private static class CurrentData {
        private Double temperature;
        private String skyText;
        private String humidity;
        private String windText;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StedDto {
//        private String id;
//        private String hovedtype;
//        private String undertype;
        private String primærtnavn;
//        private String primærnavnestatus;
//
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
//        private Instant ændret;
//
//        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX", timezone = "UTC")
//        private Instant geo_ændret;
//
//        private int geo_version;
//        private String href;
        private Egenskaber egenskaber;
        private List<Double> visueltcenter;
//        private List<Double> bbox;
        private List<Kommune> kommuner;
//        private List<String> sekundærenavne;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Egenskaber {
//            private int bebyggelseskode;
            private Integer indbyggerantal;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Kommune {
//            private String href;
//            private String kode;
            private String navn;
        }
    }
}