package bensadon.weather.api;

import bensadon.weather.model.WindyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface WindyService
{
    @GET("api/v3/webcams")
    Call<WindyResponse> getWebcams(
            @Query("nearby") String nearby,
            @Query("limit") int limit,
            @Query("include") String include,
            @Header("x-windy-api-key") String apiKey
    );
}