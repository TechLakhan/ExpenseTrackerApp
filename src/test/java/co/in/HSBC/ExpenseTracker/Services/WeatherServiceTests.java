package co.in.HSBC.ExpenseTracker.Services;

import co.in.HSBC.ExpenseTracker.services.WeatherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherServiceTests {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void testWeatherForecast() {
        weatherService.getWeatherForecast("Mumbai");
    }
}
