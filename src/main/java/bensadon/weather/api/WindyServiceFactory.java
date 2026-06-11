package bensadon.weather.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WindyServiceFactory
{
    public WindyService create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.windy.com/webcams/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        return retrofit.create(WindyService.class);
    }
}