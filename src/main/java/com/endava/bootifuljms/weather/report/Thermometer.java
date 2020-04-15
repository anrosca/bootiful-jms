package com.endava.bootifuljms.weather.report;

import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class Thermometer {

    public Temperature getCurrentTemperature() {
        double temperature = ThreadLocalRandom.current().nextDouble(-40, 50);
        return new Temperature(truncateToOneDecimalPlace(temperature));
    }

    private double truncateToOneDecimalPlace(double temperature) {
        return ((int) (temperature * 10)) / 10.0;
    }
}
