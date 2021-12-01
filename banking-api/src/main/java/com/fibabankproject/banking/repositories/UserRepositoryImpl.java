package com.fibabankproject.banking.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.fibabankproject.banking.domain.Account;
import com.fibabankproject.banking.domain.User;
import com.fibabankproject.banking.exceptions.EtAuthException;
import com.fibabankproject.banking.exceptions.EtBadRequestException;
import com.fibabankproject.banking.exceptions.EtResourceNotFoundException;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final String SQL_CREATE = "INSERT INTO BA_USERS(USER_ID, TCKN, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('BA_USERS_SEQ'), ?, ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_TCKN = "SELECT COUNT(*) FROM BA_USERS WHERE TCKN = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, TCKN, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM BA_USERS WHERE USER_ID = ?";
    private static final String SQL_FIND_BY_TCKN = "SELECT USER_ID, TCKN, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
            "FROM BA_USERS WHERE TCKN = ?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM BA_USERS";
    private static final String SQL_UPDATE_BY_TCKN = "UPDATE BA_USERS SET FIRST_NAME = ? , LAST_NAME = ? , EMAIL = ? , PASSWORD = ? WHERE TCKN = ? ";
    
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer create(String tckn, String firstName, String lastName, String email, String password) throws EtAuthException {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, tckn);
				ps.setString(2, firstName);
				ps.setString(3, lastName);
				ps.setString(4, email);
				ps.setString(5, password);
				return ps;
			}, keyHolder);
			return (Integer) keyHolder.getKeys().get("USER_ID");
			
		}catch(Exception e){
			throw new EtAuthException("Failed to create account. Invalid details.");
		}
	}

    @Override
    public User findByTcknAndPassword(String tckn, String password) throws EtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_TCKN, new Object[]{tckn}, userRowMapper);
            if(!password.equals(user.getPassword()))
                throw new EtAuthException("Invalid tckn/password");
            return user;
        }catch (EmptyResultDataAccessException e) {
            throw new EtAuthException("Invalid tckn/password");
        }
    }

	@Override
	public List<User> getAllUsers() throws EtResourceNotFoundException {
		return jdbcTemplate.query(SQL_FIND_ALL_USERS, new Object[]{}, userRowMapper);
	}
	
	@Override
	public Integer getCountByTckn(String tckn) {
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_TCKN, new Object[]{tckn}, Integer.class);
	}
	
	@Override
	public User findById(Integer userId) {
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
	}

	@Override
	public void updateUser(User user) {
        try {
        	User checkUser = jdbcTemplate.queryForObject(SQL_FIND_BY_TCKN, new Object[]{user.getTckn()}, userRowMapper);
            if(user.getTckn().equals(checkUser.getTckn())) {
            	jdbcTemplate.update(SQL_UPDATE_BY_TCKN, new Object[]{user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getTckn()});
   
            }else {       
            }
        }catch (Exception e) {
            throw new EtBadRequestException("Invalid request");
        }
	}
	
	private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
		return new User (rs.getInt("USER_ID"),
				rs.getString("TCKN"),
				rs.getString("FIRST_NAME"),
				rs.getString("LAST_NAME"),
				rs.getString("EMAIL"),
				rs.getString("PASSWORD")
				);
	
	});

}
