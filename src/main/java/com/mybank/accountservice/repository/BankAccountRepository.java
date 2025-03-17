package com.mybank.accountservice.repository;

import com.mybank.accountservice.entity.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, UUID> {

    Optional<BankAccountEntity> findByIban(String iban);
}
