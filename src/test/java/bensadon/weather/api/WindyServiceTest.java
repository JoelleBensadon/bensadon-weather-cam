package bensadon.weather.api;

import bensadon.weather.model.WindyResponse;
import com.andrewoid.apikeys.ApiKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindyServiceTest
{
    @Test
    void getWebcams()
    {
        // Given
        ApiKey apiKey = new ApiKey("windy");
        WindyService service = new WindyServiceFactory().create();

        // When
        WindyResponse response = service.getWebcams(
                "40.6526006,-73.9497211,10",
                5,
                "categories,images,location",
                apiKey.get()
        ).blockingGet();

        // Then
        assertNotNull(response.getWebcams());
        assertNotNull(response.getWebcams().get(0).getImages().getCurrent().getPreview());
    }
}