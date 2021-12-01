package com.fibabankproject.banking.repositories;

import java.util.Date;
import java.util.List;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.domain.Transaction;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {


    private static final String SQL_FIND_ALL = "SELECT TRANSACTION_ID, ACCOUNT_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND ACCOUNT_ID = ?";
    private static final String SQL_FIND_BY_ID = "SELECT TRANSACTION_ID, ACCOUNT_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND ACCOUNT_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_CREATE = "INSERT INTO ET_TRANSACTIONS (TRANSACTION_ID, ACCOUNT_ID, USER_ID, AMOUNT, NOTE, TRANSACTION_DATE) VALUES(NEXTVAL('ET_TRANSACTIONS_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE ET_TRANSACTIONS SET AMOUNT = ?, NOTE = ?, TRANSACTION_DATE = ? WHERE USER_ID = ? AND ACCOUNT_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND ACCOUNT_ID = ? AND TRANSACTION_ID = ?";
    private static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM BA_ACCOUNTS WHERE ACCOUNT_ID = ?";
    private static final String SQL_UPDATE_ACCOUNT_BALANCE = "UPDATE BA_ACCOUNTS SET BALANCE = ? WHERE ACCOUNT_ID = ?";
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId, categoryId}, transactionRowMapper);
    }

    @Override
    public Transaction findById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId, transactionId}, transactionRowMapper);
        }catch (Exception e) {
            throw new EtResourceNotFoundException("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, categoryId);
                ps.setInt(2, userId);
                ps.setDouble(3, amount);
                ps.setString(4, note);
                ps.setLong(5, transactionDate);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public Boolean transferMoney(Integer senderAccountID, Integer receiverAccountID, Double amount, String note) throws EtBadRequestException {
        try {
        	Account senderAccount = jdbcTemplate.queryForObject(SQL_GET_ACCOUNT_BY_ID, new Object[]{senderAccountID}, Account.class);
        	Account receiverAccount = jdbcTemplate.queryForObject(SQL_GET_ACCOUNT_BY_ID, new Object[]{receiverAccountID}, Account.class);
        	
            if(senderAccount.getUsrId() == receiverAccount.getUsrId()) {
            	
            	if(senderAccount.getCurrency() == receiverAccount.getCurrency()) {
            		create(senderAccount.getUsrId(),0, amount*(-1), "Hesaplarım arası giden para", new Date().getTime());
            		create(receiverAccount.getUsrId(),0, amount*(1), "Hesaplarım arası gelen para", new Date().getTime());

            		Double newSenderBalance = senderAccount.getBalance()-amount;
            		Double newReceiverBalance = receiverAccount.getBalance()+amount;
            		jdbcTemplate.queryForObject(SQL_UPDATE_ACCOUNT_BALANCE, new Object[]{newSenderBalance, senderAccountID}, transactionRowMapper);
            		jdbcTemplate.queryForObject(SQL_UPDATE_ACCOUNT_BALANCE, new Object[]{newReceiverBalance, receiverAccountID}, transactionRowMapper);
            	}else {
            		return false;
            	}
            	
            }else {
            	
            	if(senderAccount.getCurrency() == receiverAccount.getCurrency()) {
            		if(!note.isEmpty()) {
                		create(senderAccount.getUsrId(),0, amount*(-1), note, new Date().getTime());
                		create(receiverAccount.getUsrId(),0, amount*(1), note, new Date().getTime());
                		
                		Double newSenderBalance = senderAccount.getBalance()-amount;
                		Double newReceiverBalance = receiverAccount.getBalance()+amount;
                		jdbcTemplate.queryForObject(SQL_UPDATE_ACCOUNT_BALANCE, new Object[]{newSenderBalance, senderAccountID}, transactionRowMapper);
                		jdbcTemplate.queryForObject(SQL_UPDATE_ACCOUNT_BALANCE, new Object[]{newReceiverBalance, receiverAccountID}, transactionRowMapper);
            		}else {
            			return false;
            		}
            	}else {
            		return false;
            	}
            }
        	return false;
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }
    
    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{transaction.getAmount(), transaction.getNote(), transaction.getTransactionDate(), userId, categoryId, transactionId});
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundException {
        int count = jdbcTemplate.update(SQL_DELETE, new Object[]{userId, categoryId, transactionId});
        if(count == 0)
            throw new EtResourceNotFoundException("Transaction not found");
    }

    private RowMapper<Transaction> transactionRowMapper = ((rs, rowNum) -> {
        return new Transaction(rs.getInt("TRANSACTION_ID"),
                rs.getInt("SNDR_ACCOUNT_ID"),
                rs.getInt("SNDR_USER_ID"),
                rs.getInt("RCPT_ACCOUNT_ID"),
                rs.getInt("RCPT_USER_ID"),
                rs.getDouble("AMOUNT"),
                rs.getString("NOTE"),
                rs.getLong("TRANSACTION_DATE"));
    });

}
