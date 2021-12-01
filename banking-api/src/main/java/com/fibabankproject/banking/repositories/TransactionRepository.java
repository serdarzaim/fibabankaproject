package com.fibabankproject.banking.repositories;

import java.util.List;

import com.fibabankproject.banking.domain.Transaction;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;

public interface TransactionRepository {

    List<Transaction> findAll(Integer userId, Integer accountId);

    Transaction findById(Integer userId, Integer accountId, Integer transactionId) throws EtResourceNotFoundException;

    Integer create(Integer senderAccountId, Integer senderUserId, Integer recipientAccountId, Integer recipientUserId, Double amount, String note, Long transactionDate) throws EtBadRequestException;

    void update(Integer userId, Integer accountId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void removeById(Integer userId, Integer accountId, Integer transactionId) throws EtResourceNotFoundException;
    
    Boolean transferMoney(Integer senderAccountID, Integer receiverAccountID, Double amount, String note);

}
