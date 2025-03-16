package com.mybank.accountservice.controller;

import com.mybank.accountservice.api.AccountApi;
import com.mybank.accountservice.api.model.BankAccountResponse;
import com.mybank.accountservice.api.model.BankAccountTotalRequest;
import com.mybank.accountservice.api.model.BankAccountTotalResponse;
import com.mybank.accountservice.api.model.CurrencyCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController implements AccountApi {

    @Override
    public ResponseEntity<BankAccountResponse> getBankAccountBalanceByCurrency(String iban, CurrencyCode currencyCode) {
        return null;
    }

    @Override
    public ResponseEntity<BankAccountTotalResponse> getBankAccountTotal(BankAccountTotalRequest bankAccountTotalRequest) {
        return null;
    }
}
