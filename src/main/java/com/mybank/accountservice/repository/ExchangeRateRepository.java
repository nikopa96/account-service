package com.mybank.accountservice.repository;

import com.mybank.accountservice.entity.ExchangeRateId;
import com.mybank.accountservice.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, ExchangeRateId> {

    List<ExchangeRateEntity> findAllByBaseCurrency(String baseCurrency);
}
