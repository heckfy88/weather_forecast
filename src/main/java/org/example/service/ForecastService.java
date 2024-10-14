package org.example.service;

import java.io.IOException;

public interface ForecastService {

    String getForecast(
            Double latitude,
            Double longitude
    ) throws IOException, InterruptedException;

    String getTemperature(
            Double latitude,
            Double longitude
    ) throws IOException, InterruptedException;

    String getAverageTemperature(
            Double latitude,
            Double longitude,
            Integer limit
    ) throws IOException, InterruptedException;
}
