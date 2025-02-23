package dat.services;

import dat.dao.ActivityDAO;
import dat.dto.ActivityDTO;
import dat.dto.CityInfoDTO;
import dat.dto.WeatherDTO;
import dat.entities.Activity;
import dat.entities.CityInfo;
import dat.entities.WeatherInfo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.*;

public class ActivityService {

    private final CityAndWeatherService cityAndWeatherService;
    private final ActivityDAO activityDAO;
    private final ExecutorService executorService;

    public ActivityService(CityAndWeatherService cityAndWeatherService, ActivityDAO activityDAO) {
        this.cityAndWeatherService = cityAndWeatherService;
        this.activityDAO = activityDAO;
        this.executorService = Executors.newFixedThreadPool(2);
    }

    public ActivityDTO createActivity(String city, String exerciseType, int duration, double distance, String comment) {
        Future<WeatherDTO> weatherFuture = executorService.submit(() -> {
            String weatherJson = cityAndWeatherService.getDataFromURL(System.getenv("API_KEY_WEATHER"), city);
            return cityAndWeatherService.getWeatherDTO(weatherJson);
        });

        Future<CityInfoDTO> cityFuture = executorService.submit(() -> {
            String cityJson = cityAndWeatherService.getDataFromURL(System.getenv("API_KEY_CITY"), city);
            return cityAndWeatherService.getCityData(cityJson);
        });

        try {
            WeatherDTO weatherDTO = weatherFuture.get();
            CityInfoDTO cityInfoDTO = cityFuture.get();

            CityInfo cityInfo = new CityInfo(null, cityInfoDTO.getPrimærtnavn(),
                    cityInfoDTO.getVisueltcenter().toString(),
                    cityInfoDTO.getKommuner().get(0).getNavn(),
                    cityInfoDTO.getEgenskaber().getIndbyggerantal(),
                    null);

            WeatherInfo weatherInfo = new WeatherInfo(null, weatherDTO.getCurrentData().getTemperature(),
                    weatherDTO.getCurrentData().getSkyText(),
                    weatherDTO.getCurrentData().getHumidity(),
                    weatherDTO.getCurrentData().getWindText());

            // Create and persist Activity entity
            Activity activity = Activity.builder()
                    .exerciseDate(LocalDate.now())
                    .exerciseType(exerciseType)
                    .timeOfDay(determineTimeOfDay())
                    .duration(duration)
                    .distance(distance)
                    .comment(comment)
                    .city(cityInfo)
                    .weather(weatherInfo)
                    .build();

            activityDAO.create(activity);

            return new ActivityDTO(
                    activity.getExerciseDate(),
                    activity.getExerciseType(),
                    activity.getTimeOfDay(),
                    activity.getDuration(),
                    activity.getDistance(),
                    activity.getComment(),
                    weatherDTO.getCurrentData(),
                    cityInfoDTO
            );

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        return null;
    }

    private String determineTimeOfDay() {
        int hour = LocalTime.now().getHour();
        if (hour < 12) {
            return "Morning";
        } else if (hour < 18) {
            return "Afternoon";
        } else {
            return "Evening";
        }
    }
}
