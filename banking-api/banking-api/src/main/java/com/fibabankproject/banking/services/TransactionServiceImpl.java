package com.fibabankproject.banking.services;

import java.util.List;

import com.fibabankproject.banking.domain.Transaction;
import com.fibabankproject.banking.repositories.TransactionRepository;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{

	@Autowired
    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer accountId) {
        return transactionRepository.findAll(userId, accountId);
    }

    @Override
    public Transaction fetchTransactionById(Integer userId, Integer accountId, Integer transactionId) throws EtResourceNotFoundException {
        return transactionRepository.findById(userId, accountId, transactionId);
    }

    @Override
    public Transaction addTransaction(Integer userId, Integer accountId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        int transactionId = transactionRepository.create(userId, accountId, amount, note, transactionDate);
        return transactionRepository.findById(userId, accountId, transactionId);
    }

    @Override
    public void updateTransaction(Integer userId, Integer accountId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        transactionRepository.update(userId, accountId, transactionId, transaction);
    }

    @Override
    public void removeTransaction(Integer userId, Integer accountId, Integer transactionId) throws EtResourceNotFoundException {
        transactionRepository.removeById(userId, accountId, transactionId);
    }

}
