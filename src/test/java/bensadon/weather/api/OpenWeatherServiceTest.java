package bensadon.weather.api;

import bensadon.weather.model.GeoLocation;
import bensadon.weather.model.WeatherResponse;
import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenWeatherServiceTest
{
    @Test
    void getLocation()
    {
        // Given
        ApiKey apiKey = new ApiKey();
        OpenWeatherService service = new OpenWeatherServiceFactory().create();

        // When
        List<GeoLocation> locations = service.getLocation(
                "Brooklyn,NY,US",
                1,
                apiKey.get()
        ).blockingGet();

        // Then
        GeoLocation location = locations.get(0);
        assertEquals("Brooklyn", location.getName());
        assertNotEquals(0, location.getLat());
        assertNotEquals(0, location.getLon());
    }

    @Test
    void getWeather()
    {
        // Given
        ApiKey apiKey = new ApiKey();
        OpenWeatherService service = new OpenWeatherServiceFactory().create();

        // When
        WeatherResponse weather = service.getWeather(
                40.6526006,
                -73.9497211,
                apiKey.get(),
                "imperial"
        ).blockingGet();

        // Then
        assertNotNull(weather.getMain());
        assertNotNull(weather.getWeather().get(0).getDescription());
    }
}