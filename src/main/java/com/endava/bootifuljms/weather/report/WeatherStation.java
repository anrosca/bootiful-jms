package com.endava.bootifuljms.weather.report;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeatherStation {
    private final Thermometer thermometer;

    public List<WeatherData> getMeasurementsFor(String cityName, LocalDate date) {
        List<WeatherData> weatherData = new ArrayList<>();
        Temperature temperature = thermometer.getCurrentTemperature();
        weatherData.add(WeatherData.newBuilder()
                .withTitle(makeTitle(cityName, date))
                .withValue(temperature.getValue())
                .withRangeMin(-40)
                .withRangeMax(50)
                .withFirstSubRangeMin(-40)
                .withFirstSubRangeMax(0)
                .withSecondSubRangeMin(0)
                .withSecondSubRangeMax(15)
                .withThirdSubRangeMin(15)
                .withThirdSubRangeMax(50)
                .build());
        return weatherData;
    }

    private String makeTitle(String cityName, LocalDate date) {
        return String.format("%s - Temperature Report - %s", cityName, date);
    }
}
