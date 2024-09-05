package co.in.HSBC.journalapp.Services;

import co.in.HSBC.journalapp.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class WeatherServiceTests {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void testWeatherForecast() {
        weatherService.getWeatherForecast("Mumbai");
    }
}
