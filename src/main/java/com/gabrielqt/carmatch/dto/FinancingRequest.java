package com.gabrielqt.carmatch.dto;

public record FinancingRequest(
        Double downPayment,
        Integer installments
) {
}