package com.gradle.KeyStrokeAnalysis.TimeAnalysis;

import lombok.AllArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
public class TimeDiff {
    public String format;

    public Long getTimeDiff(String startTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        Duration duration = Duration.between(LocalTime.parse(startTime, formatter), LocalTime.parse(endTime, formatter));
        return duration.toMillis();  // return in milliseconds
    }
}