package com.example.transaction.commision.configuration;

import com.example.transaction.commision.TransactionCommissionApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TransactionCommissionApplication.class)
public class TestConfig {
}
