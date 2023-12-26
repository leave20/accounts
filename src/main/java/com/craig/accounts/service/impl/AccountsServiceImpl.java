package com.craig.accounts.service.impl;

import com.craig.accounts.dto.AccountDto;
import com.craig.accounts.dto.CustomerDto;
import com.craig.accounts.entities.Account;
import com.craig.accounts.entities.Customer;
import com.craig.accounts.exception.CustomerAlreadyExistException;
import com.craig.accounts.exception.ResourceNotFoundException;
import com.craig.accounts.mapper.AccountsMapper;
import com.craig.accounts.mapper.CustomerMapper;
import com.craig.accounts.repository.AccountRepository;
import com.craig.accounts.repository.CustomerRepository;
import com.craig.accounts.service.IAccountsService;
import com.craig.accounts.util.AccountProcessor;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        customerRepository.findByMobileNumber(customerDto.getMobileNumber())
                .ifPresentOrElse(
                        existingCustomer  -> {
                            throw new CustomerAlreadyExistException("Customer already registered with given mobile number"
                                    + customerDto.getMobileNumber());
                        },
                        () -> {
                            Customer savedCustomer = customerRepository.save(customer);
                            accountRepository.save(AccountProcessor.createNewAccount(savedCustomer));
                        }
                );
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountsMapper.mapToAccountDto(account, new AccountDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        return Optional.ofNullable(customerDto.getAccountDto())
                .map(AccountDto::getAccountNumber)
                .map(accountRepository::findById)
                .map(accountOptional -> accountOptional.map(account -> {
                    AccountsMapper.mapToAccount(customerDto.getAccountDto(), account);
                    return accountRepository.save(account);
                }))
                .flatMap(Function.identity())
                .map(Account::getCustomerId)
                .map(customerId -> customerRepository.findById(customerId)
                        .map(customer -> {
                            CustomerMapper.mapToCustomer(customerDto, customer);
                            customerRepository.save(customer);
                            return true;
                        })
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString()))
                )
                .orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", customerDto.getAccountDto().getAccountNumber().toString()));
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
