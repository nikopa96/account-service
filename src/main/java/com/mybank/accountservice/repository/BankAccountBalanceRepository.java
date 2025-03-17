package com.mybank.accountservice.repository;

import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountBalanceRepository extends JpaRepository<BankAccountBalanceEntity, UUID> {

    @Query("select b from BankAccountBalanceEntity b " +
            "where b.bankAccount.iban = :iban and b.currencyCode = :currencyCode")
    Optional<BankAccountBalanceEntity> findBankAccountBalanceByIbanAndCurrency(String iban, String currencyCode);
}
