package com.mybank.accountservice.repository;

import com.mybank.accountservice.entity.ConversionRateEntity;
import com.mybank.accountservice.entity.ConversionRateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversionRateRepository extends JpaRepository<ConversionRateEntity, ConversionRateId> {

    List<ConversionRateEntity> findAllByCurrencyCodeFrom(String currencyCodeFrom);
}
