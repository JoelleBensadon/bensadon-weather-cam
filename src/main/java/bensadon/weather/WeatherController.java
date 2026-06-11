package bensadon.weather;

import bensadon.weather.api.OpenWeatherService;
import bensadon.weather.api.WindyService;
import bensadon.weather.model.GeoLocation;
import bensadon.weather.model.WeatherResponse;
import bensadon.weather.model.WindyResponse;
import bensadon.weather.model.WindyWebcam;
import com.andrewoid.apikeys.ApiKey;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.swing.*;
import java.net.URL;
import java.util.List;

public class WeatherController {
    private final OpenWeatherService openWeatherService;
    private final WindyService windyService;
    private final JTextField searchField;
    private final JLabel tempValueLabel;
    private final JLabel feelsLikeValueLabel;
    private final JLabel descriptionValueLabel;
    private final JPanel imagePanel;

    public WeatherController(OpenWeatherService openWeatherService,
                             WindyService windyService,
                             JTextField searchField,
                             JLabel tempValueLabel,
                             JLabel feelsLikeValueLabel,
                             JLabel descriptionValueLabel,
                             JPanel imagePanel) {
        this.openWeatherService = openWeatherService;
        this.windyService = windyService;
        this.searchField = searchField;
        this.tempValueLabel = tempValueLabel;
        this.feelsLikeValueLabel = feelsLikeValueLabel;
        this.descriptionValueLabel = descriptionValueLabel;
        this.imagePanel = imagePanel;
    }

    public void searchWeather() {
        ApiKey apiKey = new ApiKey();

        openWeatherService.getLocation(searchField.getText(), 1, apiKey.get())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        this::handleLocation,
                        Throwable::printStackTrace
                );
    }

    private void handleLocation(List<GeoLocation> locations) {
        GeoLocation location = locations.get(0);
        ApiKey apiKey = new ApiKey();

        openWeatherService.getWeather(
                        location.getLat(),
                        location.getLon(),
                        apiKey.get(),
                        "imperial"
                )
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        this::handleWeather,
                        Throwable::printStackTrace
                );

        String nearby = location.getLat() + "," + location.getLon() + ",10";

        windyService.getWebcams(
                        nearby,
                        5,
                        "categories,images,location",
                        new ApiKey("windy").get())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.from(SwingUtilities::invokeLater))
                .subscribe(
                        this::handleWebcams,
                        Throwable::printStackTrace
                );
    }

    private void handleWeather(WeatherResponse weather) {
        tempValueLabel.setText(String.valueOf(weather.getMain().getTemp()));
        feelsLikeValueLabel.setText(String.valueOf(weather.getMain().getFeelsLike()));
        descriptionValueLabel.setText(weather.getWeather().get(0).getDescription());
    }

    private void handleWebcams(WindyResponse windyResponse) {
        imagePanel.removeAll();

        for (WindyWebcam webcam : windyResponse.getWebcams()) {
            try {
                JLabel titleLabel = new JLabel(webcam.getTitle());
                imagePanel.add(titleLabel);

                String imageUrlText = webcam.getImages().getCurrent().getPreview();
                URL imageUrl = new URL(imageUrlText);

                JLabel imageLabel = new JLabel(new ImageIcon(imageUrl));
                imagePanel.add(imageLabel);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        imagePanel.revalidate();
        imagePanel.repaint();
    }

}