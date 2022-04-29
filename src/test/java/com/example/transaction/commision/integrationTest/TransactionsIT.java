package com.example.transaction.commision.integrationTest;

import com.example.transaction.commision.common.dto.CommissionDto;
import com.example.transaction.commision.common.response.ErrorResponse;
import com.example.transaction.commision.configuration.IT;
import com.example.transaction.commision.configuration.common.BaseIT;
import com.example.transaction.commision.configuration.dto.TransactionDtoBuilder;
import com.example.transaction.commision.repository.DummyDatabaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.example.transaction.commision.configuration.common.BaseIT.Resources.V1_TRANSACTIONS_COMMISSIONS;
import static org.assertj.core.api.Assertions.assertThat;

@IT
class TransactionsIT extends BaseIT {

    @Autowired
    private DummyDatabaseRepository databaseRepository;

    @BeforeEach
    void cleanUpDB() {
        this.databaseRepository.deleteAll();
    }

    @Test
    void getCommissions_shouldGetCommissionDto() {
        //given
        final var dto = new TransactionDtoBuilder().build();

        var requestSpecification = getRequestSpecification().body(dto);

        //when
        final var response = postResponse(requestSpecification, V1_TRANSACTIONS_COMMISSIONS.build());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isNotNull();

        var resultDto = response.getBody().as(CommissionDto.class);
        assertThat(resultDto.getAmount()).isEqualTo("1.25");
        assertThat(resultDto.getCurrency()).isEqualTo("EUR");
    }

    @Test
    void getCommissions_shouldReturnBadRequest_whenNotProperCurrency() {
        //given
        final var dto = new TransactionDtoBuilder()
                .withCurrency("USD")
                .build();

        var requestSpecification = getRequestSpecification().body(dto);

        //when
        final var response = postResponse(requestSpecification, V1_TRANSACTIONS_COMMISSIONS.build());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody()).isNotNull();

        var resultDto = response.getBody().as(ErrorResponse.class);
        assertThat(resultDto.getMessage()).isEqualTo("Bad request error");
        assertThat(resultDto.getDescription()).isEqualTo("Provided currency is not EUR");
    }

    @Test
    void getCommissions_shouldReturnBadRequest_whenDtoHasEmptyField() {
        //given
        final var dto = new TransactionDtoBuilder()
                .withCurrency(null)
                .build();

        var requestSpecification = getRequestSpecification().body(dto);

        //when
        final var response = postResponse(requestSpecification, V1_TRANSACTIONS_COMMISSIONS.build());

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getBody()).isNotNull();

        var resultDto = response.getBody().as(ErrorResponse.class);
        assertThat(resultDto.getMessage()).isEqualTo("Invalid data");
        assertThat(resultDto.getDescription()).isEqualTo("Field error in object 'transactionDto' on field 'currency': rejected value [null]; codes [NotNull.transactionDto.currency,NotNull.currency,NotNull.java.lang.String,NotNull]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [transactionDto.currency,currency]; arguments []; default message [currency]]; default message [must not be null]");
    }
}
