package com.craig.accounts.mapper;


import com.craig.accounts.dto.AccountDto;
import com.craig.accounts.entities.Account;

public class AccountsMapper {

    public static AccountDto mapToAccountDto(Account accounts, AccountDto accountsDto) {
    accountsDto.setAccountNumber(accounts.getAccountNumber());
    accountsDto.setAccountType(accounts.getAccountType());
    accountsDto.setBranchAddress(accounts.getBranchAddress());
    return accountsDto;
    }

    public static Account mapToAccount(AccountDto accountsDto,Account accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
