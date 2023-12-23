package com.craig.accounts.util;

import com.craig.accounts.entities.Account;
import com.craig.accounts.entities.Customer;

import java.util.Random;

import static com.craig.accounts.constants.AccountsConstants.ADDRESS;
import static com.craig.accounts.constants.AccountsConstants.SAVINGS;

public class AccountProcessor {

    private static final Random random = new Random();
    private AccountProcessor(){
    }

    public static Account createNewAccount(Customer customer){
        long randomAccNumber=1000000000L+random.nextInt(900000000);
        return new Account(
                customer.getCustomerId(),
                randomAccNumber,
                SAVINGS,
                ADDRESS
        );
    }
}
