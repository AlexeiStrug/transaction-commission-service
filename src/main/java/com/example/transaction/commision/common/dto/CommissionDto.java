package com.example.transaction.commision.common.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class CommissionDto {

    BigDecimal amount;

    @org.springframework.beans.factory.annotation.Value("${response.currency}")
    String currency;
}
