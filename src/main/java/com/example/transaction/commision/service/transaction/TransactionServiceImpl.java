package com.example.transaction.commision.service.transaction;

import com.example.transaction.commision.common.dto.CommissionDto;
import com.example.transaction.commision.common.dto.TransactionDto;
import com.example.transaction.commision.common.exception.BadRequestException;
import com.example.transaction.commision.service.commissions.CommissionService;
import com.example.transaction.commision.service.exchange.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final CommissionService commissionsService;
    private final ExchangeRateService exchangeRateService;
    private final com.example.transaction.commision.repository.DummyStorage databaseRepository;

    @org.springframework.beans.factory.annotation.Value("${response.currency}")
    private String currency;

    @Override
    public CommissionDto getCommission(TransactionDto dto) {

        if (!dto.getCurrency().equals(currency)) {
            throw new BadRequestException("Provided currency is not EUR");
        }

        final var convertedDate = convertDateToString(dto.getDate());
        final var exchangeRates = this.exchangeRateService.getExchangeRate(convertedDate);

        if (!exchangeRates.getRates().containsKey(dto.getCurrency())) {
            throw new BadRequestException(String.format("Unsupported currency - %s", dto.getCurrency()));
        }

        final var exchangeRate = exchangeRates.getRates().get(dto.getCurrency());
        dto.setAmount(dto.getAmount().multiply(exchangeRate));

        this.databaseRepository.addTransaction(dto);

        return commissionsService.calculateCommission(dto);
    }

    private String convertDateToString(LocalDate localDate) {
        return DateTimeFormatter.ISO_LOCAL_DATE.format(localDate);
    }
}
