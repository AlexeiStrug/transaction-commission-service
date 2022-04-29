package com.example.transaction.commision.service.commissions;

import com.example.transaction.commision.common.dto.CommissionDto;
import com.example.transaction.commision.common.dto.TransactionDto;

public interface CommissionService {

    CommissionDto calculateCommission(TransactionDto dto);
}
