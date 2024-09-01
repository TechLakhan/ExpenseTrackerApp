package co.in.HSBC.journalapp.services;

import co.in.HSBC.journalapp.Cache.AppCache;
import co.in.HSBC.journalapp.Constants.PlaceHolders;
import co.in.HSBC.journalapp.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static co.in.HSBC.journalapp.Constants.PlaceHolders.API_KEY;
import static co.in.HSBC.journalapp.Constants.PlaceHolders.CITY;

@Service
@Slf4j
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

//    private static final String apiUrl = "http://api.weatherstack.com/current?access_key={apiKey}&query={city}";

    public WeatherResponse getWeatherForecast(String city) {
        // Check Redis cache first
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
        }
        // Retrieve and verify API URL template from cache
        String apiUrlTemplate = appCache.appCache.get(AppCache.keys.weather_api.toString());
        // Replace placeholders with actual values
        String finalExternalApi = apiUrlTemplate.replace(CITY, city).replace(API_KEY, apiKey);

        // Fetch weather data from external API
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalExternalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();

        redisService.set("weather_of_" + city, body, 300L);
        return body;
    }
}
