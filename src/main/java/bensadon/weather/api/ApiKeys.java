package bensadon.weather.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiKeys
{
    private Properties properties;

    public ApiKeys() throws IOException
    {
        properties = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("apikey.properties");

        if (inputStream == null)
        {
            throw new IOException("Could not find apikey.properties");
        }

        properties.load(inputStream);
    }

    public String getOpenWeatherMapKey()
    {
        return properties.getProperty("openweathermap");
    }

    public String getWindyKey()
    {
        return properties.getProperty("windy");
    }
}