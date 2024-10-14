package org.example.service.impl;


import org.example.api.ForecastDto;
import org.example.api.HoursDto;
import org.example.api.WeatherForecastDto;
import org.example.service.ForecastService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.client.ForecastClient.getResponseString;
import static org.example.client.ForecastClient.getWeatherForecast;

public class ForecastServiceImpl implements ForecastService {

    @Override
    public String getForecast(
            Double latitude,
            Double longitude
    ) throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", latitude.toString());
        parameters.put("lon", longitude.toString());
        parameters.put("lang", "ru_RU");
        parameters.put("hours", "true");
        parameters.put("extra", "true");

        return getResponseString(parameters);
    }

    @Override
    public String getTemperature(
            Double latitude,
            Double longitude
    ) throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", latitude.toString());
        parameters.put("lon", longitude.toString());
        parameters.put("lang", "ru_RU");
        parameters.put("hours", "true");
        parameters.put("extra", "false");

        WeatherForecastDto weatherForecastDto = getWeatherForecast(parameters);

        return String.valueOf(weatherForecastDto.fact().temp());
    }

    @Override
    public String getAverageTemperature(
            Double latitude,
            Double longitude,
            Integer limit
    ) throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", latitude.toString());
        parameters.put("lon", longitude.toString());
        parameters.put("lang", "ru_RU");
        parameters.put("limit", limit.toString());
        parameters.put("hours", "true");
        parameters.put("extra", "false");

        WeatherForecastDto weatherForecastDto = getWeatherForecast(parameters);

        return String.valueOf(getAverage(weatherForecastDto));
    }

    private Double getAverage(WeatherForecastDto weatherForecastDto) {
        return weatherForecastDto.forecasts().stream()
                .map(ForecastDto::hours)
                .flatMap(List::stream)
                .mapToInt(HoursDto::temp)
                .average().getAsDouble();
    }
}
