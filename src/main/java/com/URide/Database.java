package com.URide;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//import org.hibernate.validator.internal.util.logging.Log_.logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

/*
 * JDBC query result row mapper for User
 */
final class UserMapper implements RowMapper<User> {
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User(rs.getLong("id"), rs.getString("name"), 
				rs.getString("email"), rs.getString("password"), rs.getInt("type"));
		return user;
	}
}
@Component
public class Database {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	String SQL;
	
	/**
	 * Saves a User to the database
	 * 
	 * @param user
	 *            User to be saved
	 */
	
	public void saveUser(User user) {
		if (user.getId() == null) {
			SQL = "insert into users (name, email, password, type) values (?, ?, ?, ?)";

			jdbcTemplate.update(SQL, user.getName(), user.getEmail(), user.getPassword(), user.getType());
		} else {
			// TODO: update
			SQL = "update users set name = ?, email = ?, password = ?, type = ?, WHERE id = ?";
			jdbcTemplate.update(SQL, user.getName(), user.getEmail(), user.getPassword(), user.getType() ,user.getId());

		}
	}
}