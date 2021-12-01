package com.fibabankproject.banking.services;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface AccountService {

    List<Account> fetchAllAccounts(Integer userId);

    Account fetchAccountById(Integer userId, Integer accountId) throws EtResourceNotFoundException;

    Account addAccount(Integer userId, String title, String description) throws EtBadRequestException;

    void updateAccount(Integer userId, Integer accountId, Account account) throws EtBadRequestException;

    void removeAccountWithAllTransactions(Integer userId, Integer accountId) throws EtResourceNotFoundException;
    
}
