package bensadon.weather.model;

import com.google.gson.annotations.SerializedName;

public class WeatherMain
{
    private double temp;

    @SerializedName("feels_like")
    private double feelsLike;

    public double getTemp()
    {
        return temp;
    }

    public double getFeelsLike()
    {
        return feelsLike;
    }
}