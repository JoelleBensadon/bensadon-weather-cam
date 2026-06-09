package bensadon.weather;

import bensadon.weather.api.ApiKeys;
import bensadon.weather.api.OpenWeatherService;
import bensadon.weather.api.OpenWeatherServiceFactory;
import bensadon.weather.api.WindyService;
import bensadon.weather.api.WindyServiceFactory;
import bensadon.weather.model.GeoLocation;
import bensadon.weather.model.WeatherResponse;
import bensadon.weather.model.WindyResponse;
import bensadon.weather.model.WindyWebcam;
import retrofit2.Response;

import java.util.List;
import java.util.Scanner;

public class WeatherApp
{
    public static void main(String[] args) throws Exception
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter location:");
        String userLocation = scanner.nextLine();

        ApiKeys apiKeys = new ApiKeys();

        OpenWeatherService openWeatherService = OpenWeatherServiceFactory.create();
        WindyService windyService = WindyServiceFactory.create();

        Response<List<GeoLocation>> locationResponse =
                openWeatherService.getLocation(userLocation, 1, apiKeys.getOpenWeatherMapKey()).execute();

        GeoLocation location = locationResponse.body().get(0);

        double lat = location.getLat();
        double lon = location.getLon();

        Response<WeatherResponse> weatherResponse =
                openWeatherService.getWeather(lat, lon, apiKeys.getOpenWeatherMapKey(), "imperial").execute();

        WeatherResponse weather = weatherResponse.body();

        System.out.println("Location: " + location.getName());
        System.out.println("Temperature: " + weather.getMain().getTemp());
        System.out.println("Feels like: " + weather.getMain().getFeelsLike());
        System.out.println("Description: " + weather.getWeather().get(0).getDescription());

        String nearby = lat + "," + lon + ",10";

        Response<WindyResponse> windyResponse =
                windyService.getWebcams(
                        nearby,
                        5,
                        "categories,images,location",
                        apiKeys.getWindyKey()
                ).execute();

        System.out.println();
        System.out.println("Webcam images:");

        for (WindyWebcam webcam : windyResponse.body().getWebcams())
        {
            System.out.println(webcam.getTitle());
            System.out.println(webcam.getImages().getCurrent().getPreview());
        }
    }
}