package com.mybank.accountservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bank_account")
@Data
public class BankAccountEntity {

    @Id
    private UUID uuid;

    private String iban;

    private String holderName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "bankAccount")
    private List<BankAccountBalanceEntity> balances;
}
