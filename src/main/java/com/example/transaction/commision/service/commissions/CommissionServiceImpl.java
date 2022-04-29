package com.example.transaction.commision.service.commissions;

import com.example.transaction.commision.common.dto.CommissionDto;
import com.example.transaction.commision.common.dto.TransactionDto;
import com.example.transaction.commision.service.commissions.calculation.ClientDiscountRule;
import com.example.transaction.commision.service.commissions.calculation.HighTurnoverRule;
import com.example.transaction.commision.service.commissions.calculation.PricingRule;
import com.example.transaction.commision.service.commissions.calculation.Rule;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommissionServiceImpl implements CommissionService {


    private final List<Rule> rules = new ArrayList<>();
    private BigDecimal commission = BigDecimal.valueOf(0);
    private boolean calculated = false;

    @org.springframework.beans.factory.annotation.Value("${response.currency}")
    private String currency;

    public CommissionServiceImpl(final com.example.transaction.commision.repository.DummyStorage dummyDatabaseRepository) {
        rules.addAll(List.of(
                        new HighTurnoverRule(dummyDatabaseRepository),
                        new ClientDiscountRule(dummyDatabaseRepository),
                        new PricingRule()
                )
        );
    }

    @Override
    public CommissionDto calculateCommission(TransactionDto dto) {
        resetState();

        this.rules.stream()
                .filter(rule -> rule.shouldRun(dto))
                .forEach(rule -> calculate(dto, rule));

        return CommissionDto.builder()
                .amount(commission.setScale(2, RoundingMode.HALF_UP))
                .currency(currency)
                .build();
    }

    private void resetState() {
        commission = BigDecimal.valueOf(0);
        calculated = false;
    }

    private void calculate(TransactionDto dto, Rule rule) {
        final var calculatedCommission = rule.evaluate(dto);
        if (!calculated) {
            commission = calculatedCommission;
            calculated = true;
        }
        if (calculatedCommission.compareTo(commission) < 0) {
            commission = calculatedCommission;
        }
    }
}
