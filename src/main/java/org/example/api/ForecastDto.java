package org.example.api;

import java.util.List;

public record ForecastDto(
        List<HoursDto> hours
) {}
