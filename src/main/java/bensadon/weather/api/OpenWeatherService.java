package bensadon.weather.api;

import bensadon.weather.model.GeoLocation;
import bensadon.weather.model.WeatherResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface OpenWeatherService
{
    @GET("geo/1.0/direct")
    Single<List<GeoLocation>> getLocation(
            @Query("q") String location,
            @Query("limit") int limit,
            @Query("appid") String apiKey
    );

    @GET("data/2.5/weather")
    Single<WeatherResponse> getWeather(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}