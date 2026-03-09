package com.banking.infraestructure.config;

import com.banking.infraestructure.adapter.out.persistence.AccountEntity;
import com.banking.infraestructure.adapter.out.persistence.AccountJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AccountJpaRepository accountRepository) {
        return args -> {
            if (accountRepository.count() == 0) {
                accountRepository.save(new AccountEntity("acc-001", "Carlos García", new BigDecimal("5000.00"), "EUR"));
                accountRepository.save(new AccountEntity("acc-002", "María López", new BigDecimal("3000.00"), "EUR"));
                System.out.println(">>> Cuentas de prueba creadas");
            }
        };
    }
}
