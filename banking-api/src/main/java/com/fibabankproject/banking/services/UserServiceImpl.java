package com.fibabankproject.banking.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.domain.Transaction;
import com.fibabankproject.banking.domain.User;
import com.fibabankproject.banking.exceptions.EtAuthException;
import com.fibabankproject.banking.repositories.UserRepository;

@Service
@Transactional

public class UserServiceImpl implements UserService{

	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User validateUser(String tckn, String password) throws EtAuthException {
		if(tckn != null) tckn = tckn.toLowerCase();
		return userRepository.findByTcknAndPassword(tckn, password);
	}
    
	@Override
	public List<User> getAllUsers() {
		return userRepository.getAllUsers();
	}
	
	@Override
	public void updateUser(User user) {
		userRepository.updateUser(user);
	}
	
	@Override
	public User registerUser(String tckn, String firstName, String lastName, String email, String password)
			throws EtAuthException {
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		if(email != null) email = email.toLowerCase();
		if(!pattern.matcher(email).matches())
			throw new EtAuthException("Email format is invalid");
		Integer count = userRepository.getCountByTckn(tckn);
		if(count > 0)
			throw new EtAuthException("TCKN already in use");
		Integer userId = userRepository.create(tckn, firstName, lastName, email, password);
		return userRepository.findById(userId);
	}

}
