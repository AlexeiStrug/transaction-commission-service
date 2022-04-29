package com.example.transaction.commision.common.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class ExchangeRateDto {
    private Boolean success;
    private Boolean historical;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}
