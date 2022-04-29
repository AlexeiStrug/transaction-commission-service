package com.example.transaction.commision.configuration.dto;

import com.example.transaction.commision.common.dto.ExchangeRateDto;
import com.example.transaction.commision.common.dto.TransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ExchangeRateDtoBuilder {
    private Boolean success = true;
    private Boolean historical = true;
    private String base = "EUR";
    private String date = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());
    private Map<String, BigDecimal> rates = Map.of("EUR", BigDecimal.valueOf(1));

    public ExchangeRateDtoBuilder withSuccess(Boolean success) {
        this.success = success;
        return this;
    }

    public ExchangeRateDtoBuilder withHistorical(Boolean historical) {
        this.historical = historical;
        return this;
    }

    public ExchangeRateDtoBuilder withBase(String base) {
        this.base = base;
        return this;
    }

    public ExchangeRateDtoBuilder withDate(String date) {
        this.date = date;
        return this;
    }

    public ExchangeRateDtoBuilder withRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
        return this;
    }

    public ExchangeRateDto build() {
        return ExchangeRateDto.builder()
                .success(this.success)
                .historical(this.historical)
                .base(this.base)
                .date(this.date)
                .rates(this.rates)
                .build();
    }
}
