package com.mybank.accountservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;

@Entity
@IdClass(ConversionRateId.class)
@Table(name = "conversion_rate")
@Data
public class ConversionRateEntity {

    @Id
    private String currencyCodeFrom;

    @Id
    private String currencyCodeTo;

    private BigInteger rateInCents;
}
