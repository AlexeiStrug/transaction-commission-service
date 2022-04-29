package com.example.transaction.commision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionCommissionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionCommissionApplication.class, args);
    }

}
