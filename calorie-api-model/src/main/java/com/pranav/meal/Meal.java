package com.pranav.meal;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meal {
    private String foodName;
    private double quantityInGram;
    private LocalDateTime timeStamp;


    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendLiteral('Z')
            .optionalEnd()
            .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
            .toFormatter();

    @JsonCreator
    public Meal(
            @JsonProperty("foodName") String foodName,
            @JsonProperty("quantityInGram") double quantityInGram,
            @JsonProperty("timeStamp") String timeStamp) {
        this.foodName = foodName;
        this.quantityInGram = quantityInGram;
        this.timeStamp = LocalDateTime.now();
    }
}
