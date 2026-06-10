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

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class WeatherFrame extends JFrame
{
    private JTextField locationField;
    private JPanel imagePanel;
    private JLabel weatherLabel;

    public WeatherFrame()
    {
        setTitle("Weather App");
        setSize(900, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        locationField = new JTextField();
        JButton searchButton = new JButton("Search");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(locationField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        weatherLabel = new JLabel("Enter a location to see the weather.");
        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(imagePanel);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(weatherLabel, BorderLayout.SOUTH);

        searchButton.addActionListener(e -> search());
        locationField.addActionListener(e -> search());
    }

    private void search()
    {
        try
        {
            imagePanel.removeAll();

            String userLocation = locationField.getText();

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

            weatherLabel.setText(
                    location.getName()
                            + " | Temp: " + weather.getMain().getTemp()
                            + " | Feels like: " + weather.getMain().getFeelsLike()
                            + " | " + weather.getWeather().get(0).getDescription()
            );

            String nearby = lat + "," + lon + ",10";

            Response<WindyResponse> windyResponse =
                    windyService.getWebcams(
                            nearby,
                            5,
                            "categories,images,location",
                            apiKeys.getWindyKey()
                    ).execute();

            for (WindyWebcam webcam : windyResponse.body().getWebcams())
            {
                JLabel title = new JLabel(webcam.getTitle());
                imagePanel.add(title);

                URL url = new URL(webcam.getImages().getCurrent().getPreview());
                JLabel imageLabel = new JLabel(new ImageIcon(url));
                imagePanel.add(imageLabel);
            }

            imagePanel.revalidate();
            imagePanel.repaint();
        } catch (Exception ex)
        {
            weatherLabel.setText("Could not find weather for that location.");
        }
    }
}