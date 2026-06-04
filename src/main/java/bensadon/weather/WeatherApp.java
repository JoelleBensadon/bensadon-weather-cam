package bensadon.weather;

import bensadon.weather.api.ApiKeys;
import bensadon.weather.api.OpenWeatherService;
import bensadon.weather.api.OpenWeatherServiceFactory;
import bensadon.weather.model.GeoLocation;
import bensadon.weather.model.WeatherResponse;
import retrofit2.Response;

import java.util.List;

public class WeatherApp
{
    public static void main(String[] args) throws Exception
    {
        ApiKeys apiKeys = new ApiKeys();
        OpenWeatherService service = OpenWeatherServiceFactory.create();

        String apiKey = apiKeys.getOpenWeatherMapKey();

        Response<List<GeoLocation>> locationResponse =
                service.getLocation("Brooklyn,NY,US", 1, apiKey).execute();

        GeoLocation location = locationResponse.body().get(0);

        Response<WeatherResponse> weatherResponse =
                service.getWeather(location.getLat(), location.getLon(), apiKey, "imperial").execute();

        WeatherResponse weather = weatherResponse.body();

        System.out.println("Temperature: " + weather.getMain().getTemp());
        System.out.println("Feels like: " + weather.getMain().getFeelsLike());
        System.out.println("Description: " + weather.getWeather().get(0).getDescription());
    }
}