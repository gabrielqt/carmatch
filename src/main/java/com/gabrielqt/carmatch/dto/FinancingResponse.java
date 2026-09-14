package com.gabrielqt.carmatch.dto;

public record FinancingResponse(
        String car,
        Double price,
        Double downPayment,
        Double financedAmount,
        Integer installments,
        Double installmentValue
) {
}