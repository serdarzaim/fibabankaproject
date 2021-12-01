package com.fibabankproject.banking.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;

@Repository
public class AccountRepositoryImpl implements AccountRepository{


    private static final String SQL_FIND_ALL = "SELECT * FROM BA_ACCOUNTS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM BA_ACCOUNTS WHERE USER_ID = ? AND ACCOUNT_ID = ? ";
    private static final String SQL_FIND_BY_ACCOUNT_ID = "SELECT * FROM BA_ACCOUNTS WHERE ACCOUNT_ID = ? ";
    private static final String SQL_CREATE = "INSERT INTO BA_ACCOUNTS (ACCOUNT_ID, USER_ID, TITLE, DESCRIPTION, BALANCE, CURRENCY) "
    		+ "VALUES(NEXTVAL('BA_ACCOUNTS_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE BA_ACCOUNTS SET TITLE = ?, DESCRIPTION = ? " +
            "WHERE USER_ID = ? AND ACCOUNT_ID = ?";
    private static final String SQL_DELETE_ACCOUNT = "DELETE FROM BA_ACCOUNTS WHERE USER_ID = ? AND ACCOUNT_ID = ?";
    private static final String SQL_DELETE_ALL_TRANSACTIONS = "DELETE FROM BA_TRANSACTIONS WHERE ACCOUNT_ID = ?";

    
    @Autowired
    JdbcTemplate jdbcTemplate;
	
	
	@Override
	public List<Account> findAll(Integer userId) throws EtResourceNotFoundException {
		return jdbcTemplate.query(SQL_FIND_ALL, new Object[]{userId}, accountRowMapper);
	}

	@Override
	public Account findById(Integer userId, Integer accountId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, accountId}, accountRowMapper);
        }catch (Exception e) {
            throw new EtResourceNotFoundException("Account not found");
        }
	}
	
	@Override
	public Account findByAccountId(Integer accountId) throws EtResourceNotFoundException {
        try {
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ACCOUNT_ID, new Object[]{ accountId}, accountRowMapper);
        }catch (Exception e) {
            throw new EtResourceNotFoundException("Account not found");
        }
	}

	@Override
	public Integer create(Integer userId, String title, String description, Integer balance, String currency) throws EtBadRequestException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, title);
                ps.setString(3, description);
                ps.setInt(4, balance);
                ps.setString(5, currency);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("ACCOUNT_ID");
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
	}

	@Override
	public void update(Integer userId, Integer accountId, Account account) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE, new Object[]{account.getTitle(), account.getDescription(), userId, accountId});
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
	}

	@Override
	public void removeById(Integer userId, Integer accountId) {
		// this.removeAllActTransactions(accountId);
	       jdbcTemplate.update(SQL_DELETE_ACCOUNT, new Object[]{userId, accountId});
	}
	
	private RowMapper<Account> accountRowMapper = ((rs, rowNum) -> {
        return new Account(rs.getInt("ACCOUNT_ID"),
                rs.getInt("USER_ID"),
                rs.getString("TITLE"),
                rs.getString("DESCRIPTION"),
                rs.getDouble("BALANCE"),
                rs.getString("CURRENCY")
                );
    });
}
