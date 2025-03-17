package com.mybank.accountservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ConversionRateId implements Serializable {

    private String currencyCodeFrom;
    private String currencyCodeTo;
}
