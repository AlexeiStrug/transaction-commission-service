package com.example.transaction.commision.service.transaction;

import com.example.transaction.commision.common.dto.TransactionDto;
import com.example.transaction.commision.common.exception.BadRequestException;
import com.example.transaction.commision.configuration.dto.CommissionDtoBuilder;
import com.example.transaction.commision.configuration.dto.ExchangeRateDtoBuilder;
import com.example.transaction.commision.configuration.dto.TransactionDtoBuilder;
import com.example.transaction.commision.service.commissions.CommissionService;
import com.example.transaction.commision.service.exchange.ExchangeRateService;
import feign.RetryableException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceSpec {

    @Mock
    CommissionService commissionsService;

    @Mock
    ExchangeRateService exchangeRateService;

    @Mock
    com.example.transaction.commision.repository.DummyStorage databaseRepository;

    @InjectMocks
    TransactionServiceImpl testedObj;

    @Test
    void getCommission_shouldReturnCommissionDto() {
        //given
        final var dto = new TransactionDtoBuilder().build();
        final var exchangeRateDto = new ExchangeRateDtoBuilder().build();
        final var commissionDto = new CommissionDtoBuilder().build();
        ReflectionTestUtils.setField(testedObj, "currency", "EUR");

        //when
        when(exchangeRateService.getExchangeRate(isA(String.class))).thenReturn(exchangeRateDto);
        when(commissionsService.calculateCommission(isA(TransactionDto.class))).thenReturn(commissionDto);


        final var result = testedObj.getCommission(dto);

        //then
        verify(exchangeRateService, times(1)).getExchangeRate(isA(String.class));
        verify(databaseRepository, times(1)).addTransaction(isA(TransactionDto.class));

        assertNotNull(result);
        assertEquals(result, commissionDto);
    }

    @Test
    void getCommission_shouldThrowException_BadRequestException_CurrencyIsNotEur() {
        //given
        final var dto = new TransactionDtoBuilder().withCurrency("PLN").build();
        ReflectionTestUtils.setField(testedObj, "currency", "EUR");

        //when & then
        assertThrows(BadRequestException.class, () -> testedObj.getCommission(dto));

        verify(exchangeRateService, never()).getExchangeRate(isA(String.class));
        verify(databaseRepository, never()).addTransaction(isA(TransactionDto.class));
    }

    @Test
    void getCommission_shouldThrowException_BadRequestException_UnsupportedCurrency() {
        //given
        final var dto = new TransactionDtoBuilder().withCurrency("PLN").build();
        final var exchangeRateDto = new ExchangeRateDtoBuilder().build();
        ReflectionTestUtils.setField(testedObj, "currency", "PLN");

        //when
        when(exchangeRateService.getExchangeRate(isA(String.class))).thenReturn(exchangeRateDto);

        //then
        assertThrows(BadRequestException.class, () -> testedObj.getCommission(dto));

        verify(exchangeRateService, times(1)).getExchangeRate(isA(String.class));
        verify(databaseRepository, never()).addTransaction(isA(TransactionDto.class));
    }

    @Test
    void getCommission_shouldThrowException_RetryableException_ExchangeServiceUnavailable() {
        //given
        final var dto = new TransactionDtoBuilder().withCurrency("PLN").build();
        ReflectionTestUtils.setField(testedObj, "currency", "PLN");

        //when
        when(exchangeRateService.getExchangeRate(isA(String.class))).thenThrow(RetryableException.class);

        //then
        assertThrows(RetryableException.class, () -> testedObj.getCommission(dto));

        verify(exchangeRateService, times(1)).getExchangeRate(isA(String.class));
        verify(databaseRepository, never()).addTransaction(isA(TransactionDto.class));
    }
}
