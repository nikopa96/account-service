package com.mybank.accountservice.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountBalanceInBigDecimal {

    private String currencyCode;
    private BigDecimal balance;
    private String convertedBalanceCurrencyCode;
    private BigDecimal convertedBalance;
}
