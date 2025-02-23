package dat;

import dat.config.HibernateConfig;
import dat.dao.ActivityDAO;
import dat.dto.ActivityDTO;
import dat.dto.CityInfoDTO;
import dat.dto.WeatherDTO;
import dat.entities.Activity;
import dat.entities.CityInfo;
import dat.entities.WeatherInfo;
import dat.services.ActivityService;
import dat.services.CityAndWeatherService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final ActivityDAO ACTIVITY_DAO = ActivityDAO.getInstance(emf);

    public static void main(String[] args) {

        CityAndWeatherService cityAndWeatherService = new CityAndWeatherService();
        ActivityService activityService = new ActivityService(cityAndWeatherService, ACTIVITY_DAO);

        String weatherAPI = System.getenv("API_KEY_WEATHER");
        String cityAPI = System.getenv("API_KEY_CITY");

//        String city = "Lyngby";
//        String result = cityAndWeatherService.getDataFromURL(weatherAPI, city);
//        String cityResult = cityAndWeatherService.getDataFromURL(cityAPI, city);
//
//        WeatherDTO weatherDTO = cityAndWeatherService.getWeatherDTO(result);
//        CityInfoDTO cityInfoDTO = cityAndWeatherService.getCityData(cityResult);
//
//        System.out.println(result);
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(weatherDTO);
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(cityResult);
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println(cityInfoDTO);


//        String exerciseType = "Højdespring";
//        int duration = 45;
//        double distance = 5.2;
//        String comment = "Den der springer højst får 12 til eksamen!";
//
//        ActivityDTO activity = activityService.createActivity(city, exerciseType, duration, distance, comment);
//        System.out.println("Generated Activity:");
//        System.out.println("Exercise Type: " + activity.getExerciseType());
//        System.out.println("City: " + activity.getCity().getPrimærtnavn());
//        System.out.println("Temperature: " + activity.getWeather().getTemperature() + "°C");
//        System.out.println("Comment: " + activity.getComment());
//
//        System.out.println("-----------------------------------------------------------------");
//        System.out.println("Activity Info:");
//        System.out.println("Exercise Date: " + activity.getExerciseDate());
//        System.out.println("Exercise Type: " + activity.getExerciseType());
//        System.out.println("Time of Day: " + activity.getTimeOfDay());
//        System.out.println("Duration: " + activity.getDuration());
//        System.out.println("Distance: " + activity.getDistance());
//        System.out.println("Comment: " + activity.getComment());

        CityInfo city = new CityInfo(null, "Copenhagen", "55.6761,12.5683", "Capital Region", 600000, null);

        // Create WeatherInfo
        WeatherInfo weather = new WeatherInfo(null, 10.5, "Cloudy", "80%", "5 km/h");

        // Create Activity
        Activity activity = Activity.builder()
                .exerciseDate(LocalDate.now())
                .exerciseType("Running")
                .timeOfDay("Morning")
                .duration(45)
                .distance(5.2)
                .comment("Great weather for a jog!")
                .city(city)
                .weather(weather)
                .build();

        ACTIVITY_DAO.create(activity);

        System.out.println("All activities:");
        ACTIVITY_DAO.findAll().forEach(System.out::println);
    }
}