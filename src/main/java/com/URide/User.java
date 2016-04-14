package com.URide;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

	private Long id;
	

	@NotNull(message = "Username cannot be null")
	@Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters long")
	@Pattern(regexp = "[\\w]*", message = "Username contains invalid characters")
	private String name;
	
	@NotNull(message = "Email must not be null")
	@NotEmpty(message = "Email must not be empty")
	@Size(max = 254, message = "Email exceeded max length")
	@Email(message = "Not a valid email")
	private String email;
	
	@NotNull(message = "Password must not be null")
	@NotEmpty(message = "Password must not be empty")
	private String password;
	
	private int type;

	public User(Long id, String name, String password, String email, int type) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.type = type;
	}
	public User(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String passwordHash) {
		this.password = passwordHash;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}
