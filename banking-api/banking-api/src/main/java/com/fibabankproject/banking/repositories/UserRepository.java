package com.fibabankproject.banking.repositories;

import com.fibabankproject.banking.domain.User;
import com.fibabankproject.banking.exceptions.EtAuthException;

public interface UserRepository {
	
    Integer create(String tckn, String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByTcknAndPassword(String tckn, String password) throws EtAuthException;

    Integer getCountByTckn(String tckn);

    User findById(Integer userId);
    
    User[] getAllUsers();
   
    Boolean updateUser(User user);
}
