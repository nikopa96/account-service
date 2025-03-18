package com.mybank.accountservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExchangeRateId implements Serializable {

    private String baseCurrency;
    private String convertibleCurrency;
}
