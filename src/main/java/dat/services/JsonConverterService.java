package dat.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dat.dto.ActivityDTO;

import java.util.List;

public class JsonConverterService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String convertToJson(List<ActivityDTO> activities) {
        try {
            return objectMapper.writeValueAsString(activities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}