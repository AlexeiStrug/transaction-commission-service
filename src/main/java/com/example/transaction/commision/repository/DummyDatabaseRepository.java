package com.example.transaction.commision.repository;

import com.example.transaction.commision.common.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyDatabaseRepository {
    private List<TransactionDto> transactions = new ArrayList<>();
    private List<Integer> clientsWithDiscount = new ArrayList<>(42);

    public void addTransaction(TransactionDto dto) {
        this.transactions.add(dto);
    }

    public List<TransactionDto> getAllTransactions() {
        return this.transactions;
    }

    public List<Integer> getClientsWithDiscount() {
        return this.clientsWithDiscount;
    }

    public void deleteAll() {
        transactions = new ArrayList<>();
        clientsWithDiscount = new ArrayList<>(42);
    }
}
