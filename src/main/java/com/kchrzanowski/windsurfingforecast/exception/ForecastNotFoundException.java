package com.kchrzanowski.windsurfingforecast.exception;

public class ForecastNotFoundException extends RuntimeException {
    public ForecastNotFoundException(String message) {
        super(message);
    }
}
