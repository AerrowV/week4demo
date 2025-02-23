package dat.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.dto.CityInfoDTO;
import dat.dto.WeatherDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CityAndWeatherService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public WeatherDTO getWeatherDTO(String json) {
        try {
            WeatherDTO weatherDTO = objectMapper.readValue(json, WeatherDTO.class);
            return weatherDTO;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public CityInfoDTO getCityData(String json) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            CityInfoDTO cityInfoDTO = objectMapper.readValue(json, CityInfoDTO[].class)[0];
            return cityInfoDTO;
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getDataFromURL(String url, String city) {
        String cityURL = url.replace("%%", city);
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(cityURL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

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
}