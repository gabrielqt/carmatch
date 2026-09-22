package com.gabrielqt.carmatch.service;

import com.gabrielqt.carmatch.dto.FinancingRequest;
import com.gabrielqt.carmatch.dto.FinancingResponse;
import com.gabrielqt.carmatch.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CarService: testes unitarios")
class CarServiceTest {

    private static final double DELTA = 0.001;

    private CarService service;

    @BeforeEach
    void setUp() {
        service = new CarService();
    }

    @Test
    @DisplayName("findAll deve retornar os 3 carros cadastrados")
    void shouldReturnAllRegisteredCars() {
        var cars = service.findAll();

        assertEquals(3, cars.size());
    }

    @Test
    @DisplayName("findById deve retornar o carro correto")
    void shouldFindCarById() {
        Car car = service.findById(3L);

        assertAll(
                () -> assertEquals("Toyota", car.brand()),
                () -> assertEquals("Corolla", car.model()),
                () -> assertEquals(2025, car.year()),
                () -> assertEquals(145000.00, car.price(), DELTA)
        );
    }

    @Test
    @DisplayName("findById deve lancar excecao para id inexistente")
    void shouldThrowWhenCarNotFound() {
        assertThrows(NoSuchElementException.class, () -> service.findById(99L));
    }

    @Test
    @DisplayName("simulate deve calcular valor financiado e parcela")
    void shouldCalculateFinancingSimulation() {
        FinancingRequest request = new FinancingRequest(25900.00, 10);

        FinancingResponse response = service.simulate(1L, request);

        assertAll(
                () -> assertEquals(100000.00, response.financedAmount(), DELTA),
                () -> assertEquals(10000.00, response.installmentValue(), DELTA)
        );
    }

    @Test
    @DisplayName("simulate deve montar a resposta com os dados do carro e da requisicao")
    void shouldBuildResponseWithCarAndRequestData() {
        FinancingRequest request = new FinancingRequest(9000.00, 48);

        FinancingResponse response = service.simulate(2L, request);

        assertAll(
                () -> assertEquals("Volkswagen Polo", response.car()),
                () -> assertEquals(89000.00, response.price(), DELTA),
                () -> assertEquals(9000.00, response.downPayment(), DELTA),
                () -> assertEquals(48, response.installments())
        );
    }

    @Test
    @DisplayName("simulate sem entrada deve financiar o valor total do carro")
    void shouldFinanceFullPriceWhenNoDownPayment() {
        FinancingRequest request = new FinancingRequest(0.0, 12);

        FinancingResponse response = service.simulate(3L, request);

        assertEquals(145000.00, response.financedAmount(), DELTA);
    }

    @Test
    @DisplayName("simulate deve lancar excecao ao simular carro inexistente")
    void shouldThrowWhenSimulatingUnknownCar() {
        FinancingRequest request = new FinancingRequest(1000.00, 12);

        assertThrows(NoSuchElementException.class, () -> service.simulate(42L, request));
    }

    @ParameterizedTest(name = "{1}x com entrada {0} -> parcela {2}")
    @CsvSource({
            "25900.00, 12, 8333.333",
            "25900.00, 24, 4166.667",
            "65900.00, 60, 1000.000"
    })
    @DisplayName("simulate deve dividir o valor financiado pelo numero de parcelas")
    void shouldCalculateInstallmentValueForDifferentTerms(double downPayment, int installments, double expected) {
        FinancingResponse response = service.simulate(1L, new FinancingRequest(downPayment, installments));

        assertEquals(expected, response.installmentValue(), DELTA);
    }
}