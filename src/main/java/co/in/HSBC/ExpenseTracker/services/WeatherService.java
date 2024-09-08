package co.in.HSBC.ExpenseTracker.services;

import co.in.HSBC.ExpenseTracker.Cache.AppCache;
import co.in.HSBC.ExpenseTracker.api.response.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static co.in.HSBC.ExpenseTracker.Constants.PlaceHolders.API_KEY;
import static co.in.HSBC.ExpenseTracker.Constants.PlaceHolders.CITY;

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

    public WeatherResponse getWeatherForecast(String city) {
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse != null) {
            return weatherResponse;
//            If API is already triggered once then it will provide the same response as weather will be the same for at least an hour.
        } else {
            String apiUrlTemplate = appCache.appCache.get(AppCache.keys.weather_api.toString());
            String finalExternalApi = apiUrlTemplate.replace(CITY, city).replace(API_KEY, apiKey);
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalExternalApi, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300L);
            }
            return body;
        }
    }
}
