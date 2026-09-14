package com.gabrielqt.carmatch.controller;


import com.gabrielqt.carmatch.model.Car;
import com.gabrielqt.carmatch.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<Car> findAll() {
        return carService.findAll();
    }

    @GetMapping("/{id}")
    public Car findById(@PathVariable Long id) {
        return carService.findById(id);
    }
}