package com.fibabankproject.banking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.exceptions.EtAuthException;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;
import com.fibabankproject.banking.repositories.AccountRepository;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public List<Account> fetchAllAccounts(Integer userId) {
		return accountRepository.findAll(userId);
	}

	@Override
	public Account addAccount(Integer userId, String title, String description,Integer balance, String currency ) throws EtBadRequestException {
		int accountId = accountRepository.create(userId, title, description, balance, currency);
		if(balance<=0)
			throw new EtAuthException("Account without balance can not be created.");
		return accountRepository.findById(userId, accountId);
	}

	@Override
	public void updateAccount(Integer userId, Integer accountId, Account account) throws EtBadRequestException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAccount(Integer userId, Integer accountId) throws EtResourceNotFoundException {
		accountRepository.removeById(userId, accountId);
	}

}
