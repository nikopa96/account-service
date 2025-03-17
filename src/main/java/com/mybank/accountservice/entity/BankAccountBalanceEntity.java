package com.mybank.accountservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.UUID;

@Entity
@Table(name = "bank_account_balance")
@Data
public class BankAccountBalanceEntity {

    @Id
    private UUID uuid;

    private String currencyCode;

    private BigInteger balance;

    @ManyToOne
    @JoinColumn(name = "bank_account_uuid")
    private BankAccountEntity bankAccount;
}
