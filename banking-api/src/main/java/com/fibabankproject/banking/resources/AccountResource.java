package com.fibabankproject.banking.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.domain.User;
import com.fibabankproject.banking.services.AccountService;

@RestController
@RequestMapping("/api/accounts")

public class AccountResource {
	
    @Autowired
    AccountService accountService;

    
    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts(HttpServletRequest request, @RequestBody Map<String, Object> req) {
    	int userId = (Integer) req.get("userId");
    	List<Account> accounts = accountService.fetchAllAccounts(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
   

    @PostMapping("/create")
    public ResponseEntity<Account> addAccount(HttpServletRequest request,
                                                @RequestBody Map<String, Object> accountMap) {
    	int userId = (Integer) accountMap.get("userId");
        String title = (String) accountMap.get("title");
        String description = (String) accountMap.get("description");
        int balance = (int) accountMap.get("balance");
        String currency = (String) accountMap.get("currency");
        Account account = accountService.addAccount(userId, title, description, balance, currency);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<Map<String, Boolean>> updateAccount(HttpServletRequest request,
                                                               @PathVariable("accountId") Integer accountId,
                                                               @RequestBody Account account) {
        int userId = (Integer) request.getAttribute("userId");
        accountService.updateAccount(userId, accountId, account);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Map<String, Boolean>> deleteAccount(HttpServletRequest request, @RequestBody Map<String, Object> req,
                                                               @PathVariable("accountId") Integer accountId) {
    	int userId = (Integer) req.get("userId");
    	accountService.removeAccount(userId, accountId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
	
}
