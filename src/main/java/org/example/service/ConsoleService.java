package org.example.service;

import org.example.domain.Operation;
import org.example.service.impl.ForecastServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleService {
    private final ForecastService forecastService = new ForecastServiceImpl();

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter the command number:");
            List<String> operations = Arrays.stream(Operation.values()).map(operation -> operation.ordinal() + ": " + operation.name()).toList();
            for (String operation : operations) {
                System.out.println(operation);
            }
            double latitude;
            double longitude;
            int limit;
            try {
                switch (Operation.getByOrdinal(scanner.nextInt())) {
                    case GET_FORECAST -> {
                        System.out.println("Enter latitude: (i.e: 10.10)");
                        latitude = scanner.nextDouble();
                        System.out.println("Enter longitude: (i.e: 10.10)");
                        longitude = scanner.nextDouble();
                        System.out.println(forecastService.getForecast(latitude, longitude));
                    }
                    case GET_CURRENT_TEMPERATURE -> {
                        System.out.println("Enter latitude: (i.e: 10.10)");
                        latitude = scanner.nextDouble();
                        System.out.println("Enter longitude: (i.e: 10.10)");
                        longitude = scanner.nextDouble();
                        System.out.println(
                                "The current temperature: " +
                                        forecastService.getTemperature(latitude, longitude));
                    }
                    case GET_AVERAGE_TEMPERATURE -> {
                        System.out.println("Enter latitude: (i.e: 10.10)");
                        latitude = scanner.nextDouble();
                        System.out.println("Enter longitude: (i.e: 10.10)");
                        longitude = scanner.nextDouble();
                        System.out.println("Enter the number of days: (i.e: 10)");
                        limit = scanner.nextInt();
                        System.out.println(
                                "The average temperature for %d days: ".formatted(limit) +
                                        "%.5s".formatted(forecastService.getAverageTemperature(latitude, longitude, limit))
                        );
                    }
                }
            } catch (Exception ex) {
                System.out.printf("Input error%n%s%nTry again%n", ex.getMessage());
                scanner.next();
            }
        }
    }
}
