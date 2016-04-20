package com.URide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
		User user = new User(rs.getLong("id"), rs.getString("name"), rs.getString("lastname"), 
				 rs.getString("password"), rs.getString("email"), rs.getInt("type"));
		return user;
	}
}

final class RiderMapper implements RowMapper<Rider> {
	
	public Rider mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long uid = rs.getLong("uid");
		
		List<Long> rides = new ArrayList<>();
		List<String> list = Arrays.asList(rs.getString("rides").replaceAll("[^-?0-9]+", " ").trim().split(" "));
		
		for(String s : list){
			if(s.length() > 0) {
				rides.add((long)Integer.parseInt(s));
			}
		}
		Rider rider = new Rider(uid, rides);
		return rider;
	} 
}

final class DriverMapper implements RowMapper<Driver> {
	
	public Driver mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long uid = rs.getLong("uid");
		
		List<Long> rides = new ArrayList<>();
		List<String> list = Arrays.asList(rs.getString("rides").replaceAll("[^-?0-9]+", " ").trim().split(" "));
		
		for(String s : list){
			if(s.length() > 0) {
				rides.add((long)Integer.parseInt(s));
			}
		}
		Driver driver = new Driver(uid, rides);
		return driver;
	} 
}

final class RideMapper implements RowMapper<Ride> {
	
	public Ride mapRow(ResultSet rs, int rowNum) throws SQLException {
		Long id = rs.getLong("id");
		Long dId = rs.getLong("did");
		String dName = rs.getString("dname");
		String date = rs.getString("date");
		String arriveTime = rs.getString("atime");
		String departTime = rs.getString("dtime");
		String finalPoint = rs.getString("fpoint");
		String initialPoint = rs.getString("ipoint");
		String crtDate = rs.getString("crtdate");
		SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
		Date createdDate = null;
		try {
			createdDate = (Date)formatter.parse(crtDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Long> rIds = new ArrayList<>();
		List<String> list = Arrays.asList(rs.getString("rids").replaceAll("[^-?0-9]+", " ").trim().split(" "));
		
		for(String s : list){
			if(s.length() > 0) {
				rIds.add((long)Integer.parseInt(s));
			}
		}
		Ride ride;
		if(date != null) {
			ride = new Ride(id, dId, dName, date, arriveTime, departTime, finalPoint, initialPoint, createdDate);
			ride.setrIds(rIds);
		}
		else {
			ride = new Ride(rIds, date, arriveTime, departTime, finalPoint, initialPoint, createdDate);
		}
		
		return ride;
	} 
}
@Component
public class Database {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	String SQL;
	
	/**
	 * Finds a user by unique ID
	 * 
	 * @param id
	 *            unique ID of user
	 * @return a User
	 */
	public User findUserById(Long id) {
		SQL = "select * from users where id = ?";
		User user;
		try {
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { id }, new UserMapper());
		} catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		return user;
	}
	/**
	 * Finds a user by the user's name
	 * 
	 * @param name
	 *            user's name (username)
	 * @return a User
	 */
	public User findUserByName(String name) {
		SQL = "select * from users where name = ?";
		User user;
		try{
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { name }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		return user;
	}
	/**
	 * Finds a user by the user's name
	 * 
	 * @param name
	 *            user's name (username)
	 * @return a User
	 */
	public User findUserByEmail(String email) {
		SQL = "select * from users where email = ?";
		User user;
		user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { email }, new UserMapper());
		return user;
	}
	/**
	 * Saves a User to the database
	 * 
	 * @param user
	 *            User to be saved
	 */
	
	public void saveUser(User user) {
		if (user.getId() == null) {
			SQL = "insert into users (name, email, password, type) values (?, ?, ?, ?)";
			try{
				jdbcTemplate.update(SQL, user.getName(), user.getEmail(), user.getPassword(), user.getType());
			}
			catch (DataAccessException e) {
				// throw 404
				throw new ResourceNotFoundException("User Table");
			}
		} else {
			// TODO: update
			SQL = "update users set name = ?, email = ?, password = ?, type = ?, WHERE id = ?";
			jdbcTemplate.update(SQL, user.getName(), user.getEmail(), user.getPassword(), user.getType() ,user.getId());

		}
	}
	/**
	 * Finds a rider by unique ID
	 * 
	 * @param id
	 *            unique ID of rider
	 * @return a Rider
	 */
	
	public Rider findRiderById(Long id) {
		SQL = "select * from riders where uid = ?";
		Rider rider;
		try {
			rider = (Rider) jdbcTemplate.queryForObject(SQL, new Object[] { id }, new RiderMapper());
		} catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("Rider");
		}
		SQL = "select * from users where id = ?";
		User user;
		try {
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { id }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		rider.setName(user.getName());
		rider.setLastName(user.getLastName());
		rider.setEmail(user.getEmail());
		rider.setPassword(user.getPassword());
		rider.setType(user.getType());
		
		return rider;
	}
	/**
	 * Finds a user by the user's name
	 * 
	 * @param name
	 *            user's name (username)
	 * @return a User
	 */
	public Rider findRiderByName(String name) {
		SQL = "select * from users where name = ?";
		User user;
		try{
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { name }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		Rider rider = findRiderById(user.getId());
		return rider;
	}
	/**
	 * Finds a user by the user's name
	 * 
	 * @param name
	 *            user's name (username)
	 * @return a User
	 */
	public Rider findRiderByEmail(String email) {
		SQL = "select * from users where email = ?";
		User user;
		try{
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { email }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		Rider rider = findRiderById(user.getId());
		return rider;
	}
	/**
	 * Saves a User to the database
	 * 
	 * @param user
	 *            User to be saved
	 */
	
	public void saveRider(Rider rider) {
		if (rider.getId() == null) {
			SQL = "insert into users (name, lastname, email, password, type) values (?, ?, ?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final PreparedStatementCreator psc = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, rider.getName());
					ps.setString(2, rider.getLastName());
					ps.setString(3, rider.getEmail());
					ps.setString(4, rider.getPassword());
					ps.setInt(5, rider.getType());
					return ps;
				}
			};
			jdbcTemplate.update(psc, keyHolder);
			String rides = "";
			for(Long tmp : rider.getRides()){
				rides = rides + tmp + " ";
			}
			SQL = "insert into riders (uid, rides) values(?, ?)";
			jdbcTemplate.update(SQL, keyHolder.getKey().intValue(), rides);
		} else {
			// TODO: update
			SQL = "update users set name = ?, lastname = ?, email = ?, password = ?, type = ? WHERE id = ?";
			try{
				jdbcTemplate.update(SQL, rider.getName(), rider.getLastName(), rider.getEmail(), rider.getPassword(), rider.getType() ,rider.getId());
			}catch (DataAccessException e) {
				// throw 404
				throw new ResourceNotFoundException("User");
			}
			String rides = "";
			for(Long tmp : rider.getRides()){
				rides = rides + tmp + " ";
			}
			SQL = "update riders set rides = ? WHERE uid = ?";
			try{
				jdbcTemplate.update(SQL, rides, rider.getId());
			}catch (DataAccessException e) {
				// throw 404
				throw new ResourceNotFoundException("User");
			}
		}
	}
	/**
	 * Finds a Driver by unique ID
	 * 
	 * @param id
	 *            unique ID of Driver
	 * @return a Driver
	 */
	public Driver findDriverById(Long id) {
		SQL = "select * from drivers where uid = ?";
		Driver driver;
		try{
			driver = (Driver) jdbcTemplate.queryForObject(SQL, new Object[] { id }, new DriverMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("Driver");
		}	
		SQL = "select * from users where id = ?";
		User user;
		try{
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { id }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		driver.setName(user.getName());
		driver.setLastName(user.getLastName());
		driver.setEmail(user.getEmail());
		driver.setPassword(user.getPassword());
		driver.setType(user.getType());
		
		return driver;
	}
	/**
	 * Finds a user by the user's name
	 * 
	 * @param name
	 *            user's name (username)
	 * @return a User
	 */
	public Driver findDriverByName(String name) {
		SQL = "select * from users where name = ?";
		User user;
		try{
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { name }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		Driver driver = findDriverById(user.getId());
		return driver;
	}
	/**
	 * Finds a user by the user's name
	 * 
	 * @param name
	 *            user's name (username)
	 * @return a User
	 */
	public Driver findDriverByEmail(String email) {
		SQL = "select * from users where email = ?";
		User user;
		try{
			user = (User) jdbcTemplate.queryForObject(SQL, new Object[] { email }, new UserMapper());
		}catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("User");
		}
		Driver driver = findDriverById(user.getId());
		return driver;
	}
	/**
	 * Saves a User to the database
	 * 
	 * @param user
	 *            User to be saved
	 */
	
	public void saveDriver(Driver driver) {
		if (driver.getId() == null) {
			SQL = "insert into users (name, lastname, email, password, type) values (?, ?, ?, ?, ?)";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final PreparedStatementCreator psc = new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, driver.getName());
					ps.setString(2, driver.getLastName());
					ps.setString(3, driver.getEmail());
					ps.setString(4, driver.getPassword());
					ps.setInt(5, driver.getType());
					return ps;
				}
			};
			jdbcTemplate.update(psc, keyHolder);
			String rides = "";
			for(Long tmp : driver.getRides()){
				rides = rides + tmp + " ";
			}
			SQL = "insert into drivers (uid, rides) values(?, ?)";
			jdbcTemplate.update(SQL, keyHolder.getKey().intValue(), rides);
		} else {
			// TODO: update
			SQL = "update users set name = ?, lastname = ?, email = ?, password = ?, type = ? WHERE id = ?";
			try {
				jdbcTemplate.update(SQL, driver.getName(), driver.getLastName(), driver.getEmail(), driver.getPassword(), driver.getType() , driver.getId());
			}catch (DataAccessException e) {
				// throw 404
				throw new ResourceNotFoundException("User");
			}
			String rides = "";
			for(Long tmp : driver.getRides()){
				rides = rides + tmp + " ";
			}
			SQL = "update drivers set rides = ? WHERE uid = ?";
			try{
				jdbcTemplate.update(SQL, rides, driver.getId());
			}catch (DataAccessException e) {
				// throw 404
				throw new ResourceNotFoundException("Drivert");
			}
		}
	}
	/**
	 * Finds a Ride by unique ID
	 * 
	 * @param id
	 *            unique ID of Ride
	 * @return a Ride
	 */
	public Ride findRideById(Long id) {
		SQL = "select * from rides where id = ?";
		Ride ride;
		try {
			ride = (Ride) jdbcTemplate.queryForObject(SQL, new Object[] { id }, new RideMapper());
		} catch (DataAccessException e) {
			// throw 404
			throw new ResourceNotFoundException("Ride");
		}
		return ride;
	}
	/**
	 * Finds a Ride by unique ID
	 * 
	 * @param id
	 *            unique ID of Ride
	 * @return a Ride
	 */
	public List<Ride> findRidesByDate(String date) {
		SQL = "select * from rides";
		List<Ride> list = jdbcTemplate.query(SQL, new RideMapper());
		List<Ride> listReturn = new ArrayList<>();
		for(Ride ride : list){
			if(ride.getDate().equals(date)){
				System.out.println("added "+ ride.getId() + " " + ride.getDate() + " " + ride.getFinalPoint());
				listReturn.add(ride);
			}
		}
		return listReturn;
	}
	/**
	 * Finds a Ride by unique ID
	 * 
	 * @param id
	 *            unique ID of Ride
	 * @return a Ride
	 */
	public List<Ride> findRidesByDateIPointAndFPoint(String date, String iPoint, String fPoint) {
		SQL = "select * from rides WHERE date = ? AND ipoint = ? AND fpoint = ?";
		System.out.println("date: " + date + " iPoint: " + iPoint + " fPoint: " + fPoint);
		List<Ride> list = jdbcTemplate.query(SQL, new Object[] { date, iPoint, fPoint },new RideMapper());
		for(Ride ride: list){
			System.out.println("id: " + ride.getId());
		}
		return list;
	}
	/**
	 * Saves a Ride to the database
	 * 
	 * @param ride
	 *            Ride to be saved
	 */
	
	public void saveRide(Ride ride) {
		String rids = "";
		for(Long tmp : ride.getrIds()){
			rids = rids + tmp.toString() + " ";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String crtDate = formatter.format(ride.getCreatedDate());
		if (ride.getId() == null) {
			if(ride.getDId() != null){
				SQL = "insert into rides (did, dname, date, rids, atime, dtime, ipoint, fpoint, crtdate) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				jdbcTemplate.update(SQL, ride.getDId(), ride.getDName(), ride.getDate(), rids, ride.getArriveTime(), ride.getDepartTime(), 
							ride.getInitialPoint(), ride.getFinalPoint(), crtDate);
			}
			else {
				SQL = "insert into rides (rids, date, atime, dtime, ipoint, fpoint, crtdate) values (?, ?, ?, ?, ?, ?, ?)";
				jdbcTemplate.update(SQL,rids, ride.getDate(), ride.getArriveTime(), ride.getDepartTime(), 
							ride.getInitialPoint(), ride.getFinalPoint(), crtDate);

			}
		} else {
			SQL = "update rides set did = ?, dname =? , rids = ?, date = ?, atime = ?, dtime = ?, ipoint = ?, fpoint = ?, crtdate = ? WHERE id = ?";
			jdbcTemplate.update(SQL, ride.getDId(), ride.getDName(), rids, ride.getDate(), ride.getArriveTime(), ride.getDepartTime(), 
						ride.getInitialPoint(), ride.getFinalPoint(), crtDate, ride.getId());
		}
	}
}