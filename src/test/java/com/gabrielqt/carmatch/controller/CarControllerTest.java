package com.gabrielqt.carmatch.controller;

import com.gabrielqt.carmatch.dto.FinancingRequest;
import com.gabrielqt.carmatch.dto.FinancingResponse;
import com.gabrielqt.carmatch.model.Car;
import com.gabrielqt.carmatch.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CarController: testes unitarios")
class CarControllerTest {
    private CarService carService;

    private CarController controller;

    @BeforeEach
    void setUp() {
        carService = mock(CarService.class);
        controller = new CarController(carService);
    }

    @Test
    @DisplayName("GET /cars deve delegar para o service")
    void shouldDelegateFindAllToService() {
        List<Car> cars = List.of(new Car(1L, "Honda", "City", 2026, 125900.00));
        when(carService.findAll()).thenReturn(cars);

        List<Car> result = controller.findAll();

        assertSame(cars, result);
        verify(carService, times(1)).findAll();
    }

    @Test
    @DisplayName("POST /cars/{id}/simulation deve repassar id e request ao service")
    void shouldDelegateSimulationToService() {
        FinancingRequest request = new FinancingRequest(10000.00, 24);
        FinancingResponse expected =
                new FinancingResponse("Toyota Corolla", 145000.00, 10000.00, 135000.00, 24, 5625.00);
        when(carService.simulate(3L, request)).thenReturn(expected);

        FinancingResponse result = controller.simulate(3L, request);

        assertEquals(expected, result);
        verify(carService).simulate(3L, request);
    }
}