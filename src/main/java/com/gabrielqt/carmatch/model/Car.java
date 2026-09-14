package com.gabrielqt.carmatch.model;

public record Car(
        Long id,
        String brand,
        String model,
        Integer year,
        Double price
) {
}