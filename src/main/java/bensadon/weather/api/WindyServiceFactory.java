package bensadon.weather.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WindyServiceFactory
{
    public static WindyService create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.windy.com/webcams/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(WindyService.class);
    }
}