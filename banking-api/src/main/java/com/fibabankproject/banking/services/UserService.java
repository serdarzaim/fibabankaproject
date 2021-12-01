package com.fibabankproject.banking.services;


import java.util.List;

import com.fibabankproject.banking.domain.User;
import com.fibabankproject.banking.exceptions.EtAuthException;

public interface UserService {
	
	User validateUser(String tckn, String password) throws EtAuthException;
	
	void updateUser(User user) throws EtAuthException;

	User registerUser(String tckn, String firstName, String lastName, String email, String password) throws EtAuthException;
	
	List<User> getAllUsers() throws EtAuthException;
}
