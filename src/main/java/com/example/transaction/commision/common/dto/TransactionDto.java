package com.example.transaction.commision.common.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC)
public class TransactionDto {

    @NotNull
    private LocalDate date;
    @Setter
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String currency;
    @NotNull
    private Integer clientId;
}
