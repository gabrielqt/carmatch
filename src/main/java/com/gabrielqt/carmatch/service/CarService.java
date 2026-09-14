package com.gabrielqt.carmatch.service;


import com.gabrielqt.carmatch.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    private final List<Car> cars = new ArrayList<>();

    public CarService() {
        cars.add(new Car(1L, "Honda", "City", 2026, 125900.00));
        cars.add(new Car(2L, "Volkswagen", "Polo", 2024, 89000.00));
        cars.add(new Car(3L, "Toyota", "Corolla", 2025, 145000.00));
    }

    public List<Car> findAll() {
        return cars;
    }

    public Car findById(Long id) {
        return cars.stream()
                .filter(car -> car.id().equals(id))
                .findFirst()
                .orElseThrow();
    }
}