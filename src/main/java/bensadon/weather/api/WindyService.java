package bensadon.weather.api;

import bensadon.weather.model.WindyResponse;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface WindyService
{
    @GET("api/v3/webcams")
    Single<WindyResponse> getWebcams(
            @Query("nearby") String nearby,
            @Query("limit") int limit,
            @Query("include") String include,
            @Header("x-windy-api-key") String apiKey
    );
}