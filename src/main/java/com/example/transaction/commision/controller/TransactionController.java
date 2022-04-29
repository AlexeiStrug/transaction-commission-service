package com.example.transaction.commision.controller;

import com.example.transaction.commision.common.dto.TransactionDto;
import com.example.transaction.commision.common.response.ErrorResponse;
import com.example.transaction.commision.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionsService;

    @PostMapping("/commissions")
    ResponseEntity getCommissions(@Validated @RequestBody TransactionDto dto, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(generateErrorResponse(errors));
        }
        var result = transactionsService.getCommission(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    private ErrorResponse generateErrorResponse(BindingResult errors) {
        return ErrorResponse.builder()
                .message("Invalid data")
                .description(Objects.requireNonNull(errors.getFieldError()).toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
