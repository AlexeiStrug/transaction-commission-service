package com.example.transaction.commision.service.commissions.calculation;

import com.example.transaction.commision.common.dto.TransactionDto;

import java.math.BigDecimal;

public interface Rule {

    BigDecimal evaluate(TransactionDto dto);

    boolean shouldRun(TransactionDto dto);
}
