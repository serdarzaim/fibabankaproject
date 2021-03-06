package com.fibabankproject.banking.services;

import java.util.List;

import com.fibabankproject.banking.domain.Transaction;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;

public interface TransactionService {


    List<Transaction> fetchAllTransactions(Integer userId, Integer accountId);

    Transaction fetchTransactionById(Integer userId, Integer accountId, Integer transactionId) throws EtResourceNotFoundException;

    void updateTransaction(Integer userId, Integer accountId, Integer transactionId, Transaction transaction) throws EtBadRequestException;

    void removeTransaction(Integer userId, Integer accountId, Integer transactionId) throws EtResourceNotFoundException;

    Boolean transferMoney(Integer senderAccountID, Integer receiverAccountID, Double amount, String note);
}
