package com.mybank.accountservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@IdClass(ExchangeRateId.class)
@Table(name = "exchange_rate")
@Data
public class ExchangeRateEntity {

    @Id
    private String baseCurrency;

    @Id
    private String convertibleCurrency;

    private BigDecimal exchangeRate;
}
