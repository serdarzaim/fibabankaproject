package com.fibabankproject.banking.repositories;

import java.util.List;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.domain.User;
import com.fibabankproject.banking.exceptions.EtAuthException;

public interface UserRepository {
	
    Integer create(String tckn, String firstName, String lastName, String email, String password) throws EtAuthException;

    User findByTcknAndPassword(String tckn, String password) throws EtAuthException;

    Integer getCountByTckn(String tckn);

    User findById(Integer userId);
    
    List<User> getAllUsers();
   
    void updateUser(User user);
    
   // void update(String tckn, String firstName, String lastName, String email, String password) throws EtBadRequestException;
}
