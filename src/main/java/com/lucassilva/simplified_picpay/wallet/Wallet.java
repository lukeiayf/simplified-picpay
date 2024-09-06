package com.lucassilva.simplified_picpay.wallet;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("WALLETS")
public record Wallet(
        @Id Long id,
        String fullName,
        Long cpf,
        String email,
        String password,
        int type,
        BigDecimal balance
) {
}
