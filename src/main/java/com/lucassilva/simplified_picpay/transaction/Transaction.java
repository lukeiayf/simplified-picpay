package com.lucassilva.simplified_picpay.transaction;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Table("TRANSACTIONS")
public record Transaction(
        @Id Long id,
        Long payer,
        Long payee,
        BigDecimal value,
        @CreatedDate LocalDateTime createdAt
) {
    public Transaction {
        value = value.setScale(2, RoundingMode.HALF_UP);
    }
}
