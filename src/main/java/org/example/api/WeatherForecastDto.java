package org.example.api;

import java.util.List;

public record WeatherForecastDto(
        FactDto fact,
        List<ForecastDto> forecasts
) {}