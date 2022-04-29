package com.example.transaction.commision.service.commissions.calculation;

import com.example.transaction.commision.common.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PricingRule implements Rule {

    private static final BigDecimal DEFAULT_COMMISSION_PERCENTAGE = BigDecimal.valueOf(0.005);
    private static final BigDecimal MINIMUM_COMMISSION = BigDecimal.valueOf(0.05);

    @Override
    public BigDecimal evaluate(TransactionDto dto) {
        final var commission = dto.getAmount().multiply(DEFAULT_COMMISSION_PERCENTAGE);
        return commission.compareTo(MINIMUM_COMMISSION) > 0 ? commission : MINIMUM_COMMISSION;
    }

    @Override
    public boolean shouldRun(TransactionDto dto) {
        return true;
    }
}
