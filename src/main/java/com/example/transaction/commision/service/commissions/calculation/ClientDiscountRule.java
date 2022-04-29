package com.example.transaction.commision.service.commissions.calculation;

import com.example.transaction.commision.common.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ClientDiscountRule implements Rule {

    private static final BigDecimal COMMISSION = BigDecimal.valueOf(0.05);

    private final com.example.transaction.commision.repository.DummyStorage databaseRepository;

    @Override
    public BigDecimal evaluate(TransactionDto dto) {
        return COMMISSION;
    }

    @Override
    public boolean shouldRun(TransactionDto dto) {
        return this.databaseRepository.getClientsWithDiscount()
                .stream()
                .anyMatch(clientId -> clientId.equals(dto.getClientId()));
    }
}
