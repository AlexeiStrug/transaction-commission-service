package com.example.transaction.commision.service.commissions.calculation;

import com.example.transaction.commision.common.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class HighTurnoverRule implements Rule {

    private static final BigDecimal COMMISSION = BigDecimal.valueOf(0.03);
    private static final BigDecimal TRANSACTION_TURN_OVER = BigDecimal.valueOf(1000);
    private final com.example.transaction.commision.repository.DummyStorage databaseRepository;

    @Override
    public BigDecimal evaluate(TransactionDto dto) {
        return COMMISSION;
    }


    @Override
    public boolean shouldRun(TransactionDto dto) {
        final var turnover = this.databaseRepository.getAllTransactions()
                .stream()
                .filter(transaction -> isSameClientId(transaction.getClientId(), dto.getClientId()))
                .filter(transaction -> isSameMonthYear(transaction.getDate(), dto.getDate()))
                .map(TransactionDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return turnover.compareTo(TRANSACTION_TURN_OVER) >= 0;
    }

    private boolean isSameClientId(Integer clientIdA, Integer clientIdB) {
        return Objects.equals(clientIdA, clientIdB);
    }

    private boolean isSameMonthYear(LocalDate dateA, LocalDate dateB) {
        return Objects.equals(dateA.getYear(), dateB.getYear()) &&
                Objects.equals(dateA.getMonth(), dateB.getMonth());
    }
}
