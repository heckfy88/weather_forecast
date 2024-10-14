package org.example.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.api.WeatherForecastDto;
import org.example.exception.UnsuccessfulResponseCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;


public class ForecastClient {

    private static final Logger logger = LoggerFactory.getLogger(ForecastClient.class);

    private static final String YANDEX_WEATHER_API_URL = "https://api.weather.yandex.ru/v2/forecast";
    private static final String YANDEX_WEATHER_API_KEY = "API_KEY";
    private static final ObjectMapper objectMapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final HttpClient client = HttpClient.newHttpClient();

    private ForecastClient() {
        throw new IllegalStateException("Utility class");
    }

    public static WeatherForecastDto getWeatherForecast(Map<String, String> parameters) throws IOException, InterruptedException {
        String payload = getResponseString(parameters);

        return objectMapper.readValue(payload, WeatherForecastDto.class);
    }

    public static String getResponseString(Map<String, String> parameters) throws IOException, InterruptedException {
        HttpResponse<String> payload;
        try {
            payload = client.send(prepareRequest(parameters), HttpResponse.BodyHandlers.ofString());
            if (payload.statusCode() != 200) {
                throw new UnsuccessfulResponseCodeException(
                        "Unsuccessful response code from Yandex Weather API: %s".formatted(payload.statusCode())
                );
            }
        } catch (Exception e) {
            logger.info("Unsuccessful request to Yandex Weather API: %s".formatted(e.getMessage()));
            throw e;
        }
        return payload.body();
    }

    private static HttpRequest prepareRequest(Map<String, String> parameters) {
        return HttpRequest.newBuilder()
                .GET()
                .header("X-Yandex-Weather-Key", YANDEX_WEATHER_API_KEY)
                .uri(URI.create(prepareUrl(YANDEX_WEATHER_API_URL, parameters)))
                .build();
    }

    private static String prepareUrl(String url, Map<String, String> parameters) {
        StringBuilder sb = new StringBuilder(url);
        boolean first = true;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (first) {
                first = false;
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }
}
