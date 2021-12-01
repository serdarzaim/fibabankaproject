package com.fibabankproject.banking.resources;

import com.fibabankproject.banking.domain.Transaction;
import com.fibabankproject.banking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/api/accounts/{accountId}/transactions")
@RequestMapping("/api/transactions")
public class TransactionResource {


    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpServletRequest request,
                                                                @PathVariable("accountId") Integer accountId) {
        int userId = (Integer) request.getAttribute("userId");
        List<Transaction> transactions = transactionService.fetchAllTransactions(userId, accountId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
    
    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(HttpServletRequest request,
                                                          @PathVariable("accountId") Integer accountId,
                                                          @PathVariable("transactionId") Integer transactionId) {
        int userId = (Integer) request.getAttribute("userId");
        Transaction transaction = transactionService.fetchTransactionById(userId, accountId, transactionId);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
    

    @PutMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> updateTransaction(HttpServletRequest request,
                                                                  @PathVariable("accountId") Integer accountId,
                                                                  @PathVariable("transactionId") Integer transactionId,
                                                                  @RequestBody Transaction transaction) {
        int userId = (Integer) request.getAttribute("userId");
        transactionService.updateTransaction(userId, accountId, transactionId, transaction);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Map<String, Boolean>> deleteTransaction(HttpServletRequest request,
                                                                  @PathVariable("accountId") Integer accountId,
                                                                  @PathVariable("transactionId") Integer transactionId) {
        int userId = (Integer) request.getAttribute("userId");
        transactionService.removeTransaction(userId, accountId, transactionId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    
 
    @PostMapping("/moneytransfer")
    public ResponseEntity<Boolean> moneyTransfer(HttpServletRequest request,
                                                      @RequestBody Map<String, Object> req) {
        int senderAccountID = (Integer) req.get("senderAccountID");
        int receiverAccountID = (Integer) req.get("receiverAccountID");
        Double amount = Double.valueOf(req.get("amount").toString());
        String note = (String) req.get("note");
        Boolean transaction = transactionService.transferMoney(senderAccountID, receiverAccountID, amount, note);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
    
    
    
}
