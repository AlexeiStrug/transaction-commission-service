package com.example.transaction.commision.configuration.dto;

import com.example.transaction.commision.common.dto.CommissionDto;

import java.math.BigDecimal;

public class CommissionDtoBuilder {

    private BigDecimal amount = BigDecimal.valueOf(1.25);

    private String currency = "EUR";

    public CommissionDtoBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public CommissionDtoBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public CommissionDto build() {
        return CommissionDto.builder()
                .amount(this.amount)
                .currency(this.currency)
                .build();
    }
}
