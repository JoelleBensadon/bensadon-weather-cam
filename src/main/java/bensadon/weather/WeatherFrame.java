package bensadon.weather;

import bensadon.weather.api.OpenWeatherServiceFactory;
import bensadon.weather.api.WindyServiceFactory;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WeatherFrame extends JFrame
{
    public WeatherFrame()
    {
        setSize(900, 700);
        setTitle("Weather App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new GridBagLayout());

        final JTextField searchField = new JTextField();
        final JButton searchButton = new JButton("Search");

        JLabel imageLabel = new JLabel("Webcams", SwingConstants.CENTER);
        imageLabel.setBorder(new LineBorder(Color.BLACK));

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setPreferredSize(new Dimension(400, 500));

        final JLabel tempTitleLabel = new JLabel("Temperature");
        final JLabel feelsLikeTitleLabel = new JLabel("Feels Like");
        final JLabel descriptionTitleLabel = new JLabel("Description");

        JLabel tempValueLabel = new JLabel("");
        JLabel feelsLikeValueLabel = new JLabel("");
        JLabel descriptionValueLabel = new JLabel("");

        WeatherController weatherController = new WeatherController(
                new OpenWeatherServiceFactory().create(),
                new WindyServiceFactory().create(),
                searchField,
                tempValueLabel,
                feelsLikeValueLabel,
                descriptionValueLabel,
                imagePanel
        );

        searchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                weatherController.searchWeather();
            }
        });

        GridBagConstraints constraints;

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(searchField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(searchButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 5;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(scrollPane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(tempTitleLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(tempValueLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(feelsLikeTitleLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(feelsLikeValueLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.anchor = GridBagConstraints.LINE_START;
        add(descriptionTitleLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(descriptionValueLabel, constraints);
    }
}