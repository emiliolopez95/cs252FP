package com.URide;

import java.util.ArrayList;
import java.util.List;

public class Rider extends User{
	private List<Long> rides;
	
	public Rider(Long id, String name, String lastName, String password,
			String email, int type, List<Long> rides) {
		super(id, name, lastName, password, email, type);
		this.rides = rides;
		
	}
	
	public Rider(String name, String lastName, String password,
			String email, int type, List<Long> rides) {
		super(name, lastName, password, email, type);
		this.rides = rides;
		
	}
	
	public Rider(Long id,List<Long> rides){
		super(id);
		this.rides = rides;
	}
	public Rider() {
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
