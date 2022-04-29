package com.example.transaction.commision.service.transaction;

import com.example.transaction.commision.common.dto.CommissionDto;
import com.example.transaction.commision.common.dto.TransactionDto;

public interface TransactionService {

    CommissionDto getCommission(TransactionDto dto);
}
