package com.fibabankproject.banking.repositories;

import java.util.List;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;
import com.fibabankproject.banking.exceptions.EtBadRequestException;

public interface AccountRepository {

    List<Account> findAll(Integer userId) throws EtResourceNotFoundException;

    Account findById(Integer userId, Integer accountId) throws EtResourceNotFoundException;
    
    Account findByAccountId(Integer accountId) throws EtResourceNotFoundException;

    Integer create(Integer userId, String title, String description, Integer balance, String currency) throws EtBadRequestException;

    void update(Integer userId, Integer accountId, Account account) throws EtBadRequestException;

    void removeById(Integer userId, Integer accountId);

}
