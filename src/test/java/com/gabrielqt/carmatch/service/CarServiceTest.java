package com.gabrielqt.carmatch.service;

import com.gabrielqt.carmatch.model.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarServiceTest {

    private final CarService service = new CarService();

    @Test
    void shouldFindAllCars() {
        var cars = service.findAll();

        assertFalse(cars.isEmpty());
    }

    @Test
    void shouldFindCarById() {
        Car car = service.findById(1L);

        assertEquals("Honda", car.brand());
        assertEquals("City", car.model());
    }
}