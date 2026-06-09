package bensadon.weather;

import bensadon.weather.api.ApiKeys;
import bensadon.weather.api.WindyService;
import bensadon.weather.api.WindyServiceFactory;
import bensadon.weather.model.WindyResponse;
import retrofit2.Response;

public class WeatherApp
{
    public static void main(String[] args) throws Exception
    {
        ApiKeys apiKeys = new ApiKeys();

        WindyService windyService = WindyServiceFactory.create();

        Response<WindyResponse> response = windyService.getWebcams(
                "40.6526006,-73.9497211",
                10,
                5,
                "categories,images,location",
                apiKeys.getWindyKey()
        ).execute();

        System.out.println(response.code());
        System.out.println(response.message());

        if (response.body() != null && response.body().getWebcams() != null)
        {
            System.out.println("Webcams found: " + response.body().getWebcams().size());

            for (int i = 0; i < response.body().getWebcams().size(); i++)
            {
                System.out.println(response.body().getWebcams().get(i).getTitle());
                System.out.println(response.body().getWebcams().get(i).getImages().getCurrent().getPreview());            }
        }
        else
        {
            System.out.println("No webcams found");
        }
    }
}