package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dat.entities.Activity;

import java.util.List;

public class JsonConverterService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(List<Activity> activities) {
        try {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(activities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}