package com.mybank.accountservice.mapper;

import com.mybank.accountservice.api.model.Account;
import com.mybank.accountservice.api.model.AccountBalance;
import com.mybank.accountservice.entity.BankAccountBalanceEntity;
import com.mybank.accountservice.entity.BankAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigInteger;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(BankAccountEntity entity);

    @Mapping(source = "balance", target = "balance", qualifiedByName = "convertCentsToBaseCurrency")
    AccountBalance toAccountBalance(BankAccountBalanceEntity entity);

    List<AccountBalance> toAccountBalances(List<BankAccountBalanceEntity> entities);

    @Named("convertCentsToBaseCurrency")
    default Double convertCentsToBaseCurrency(BigInteger value) {
        return value != null ? value.doubleValue() / 100.0 : null;
    }
}
