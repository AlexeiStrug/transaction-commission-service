package com.example.transaction.commision.configuration.dto;

import com.example.transaction.commision.common.dto.TransactionDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDtoBuilder {

    private LocalDate date = LocalDate.now();
    private BigDecimal amount = BigDecimal.valueOf(250.00);
    private String currency = "EUR";
    private Integer clientId = 1;

    public TransactionDtoBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public TransactionDtoBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionDtoBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public TransactionDtoBuilder withClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public TransactionDto build() {
        return TransactionDto.builder()
                .date(this.date)
                .amount(this.amount)
                .currency(this.currency)
                .clientId(this.clientId)
                .build();
    }
}
