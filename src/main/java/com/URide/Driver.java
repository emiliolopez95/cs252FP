package com.URide;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User{
	private List<Long> rides;
	
	public Driver(Long id, String name, String lastName, String password,
			String email, int type, List<Long> rides) {
		super(id, name, lastName, password, email, type);
		this.rides = rides;
		
	}
	
	public Driver(String name, String lastName, String password,
			String email, int type, List<Long> rides) {
		super(name, lastName, password, email, type);
		this.rides = rides;
		
	}
	public Driver(String name, String lastName, String password,
			String email, int type) {
		super(name, lastName, password, email, type);
		this.rides = new ArrayList<>();
		
	}
	
	public Driver(Long id,List<Long> rides){
		super(id);
		this.rides = rides;
	}
	public Driver() {
		super();
		this.rides = new ArrayList<>();
	}
	
	public List<Long> getRides(){
		return rides;
	}
	
	public void setRides(List<Long> rides){
		this.rides = rides;
	}
}