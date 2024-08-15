package co.in.HSBC.journalapp.apis;

import co.in.HSBC.journalapp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private final static String apiKey = "e735bd63163d7c90065b116e6b478497";

    private final static String API = "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeatherForecast(String city) {
        String finalExternalApi = API.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalExternalApi, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();
        return body;
    }

}
