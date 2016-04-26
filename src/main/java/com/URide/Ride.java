package com.URide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Ride {
	
	private Long id;
	
	private Long dId;
	private String dName;
	private List<Long> rIds;
	
	private String date;
	
	private String arriveTime;
	private String departTime;
	private String finalPoint;
	private String initialPoint;
	
	private Date createdDate;
	
	public Ride(Long id, Long dId, String dName, String date,String arriveTime, String departTime, String finalPoint, String initialPoint, Date createdDate) {
		this.id = id;
		this.dId = dId;
		this.dName = dName;
		this.date = date;
		this.arriveTime = arriveTime;
		this.departTime = departTime;
		this.finalPoint = finalPoint;
		this.initialPoint = initialPoint;
		this.createdDate = createdDate;
		this.rIds = new ArrayList<>();
	}
	public Ride(Long dId,  String date,String arriveTime, String departTime, String finalPoint, String initialPoint, Date createdDate) {
		this.dId = dId;
		this.date = date;
		this.arriveTime = arriveTime;
		this.departTime = departTime;
		this.finalPoint = finalPoint;
		this.initialPoint = initialPoint;
		this.createdDate = createdDate;
		this.rIds = new ArrayList<>();
	}
	public Ride(List<Long>rIds, String date, String arriveTime, String departTime, String finalPoint, String initialPoint, Date createdDate) {
		this.date = date;
		this.arriveTime = arriveTime;
		this.departTime = departTime;
		this.finalPoint = finalPoint;
		this.initialPoint = initialPoint;
		this.rIds = rIds;
		this.createdDate = createdDate;
	}
	
	public Ride(){
		
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDId() {
		return dId;
	}

	public void setDId(Long dId) {
		this.dId = dId;
	}

	public String getDName() {
		return dName;
	}

	public void setDName(String dName) {
		this.dName = dName;
	}
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getFinalPoint() {
		return finalPoint;
	}

	public void setFinalPoint(String destinationPoint) {
		this.finalPoint = destinationPoint;
	}

	public String getInitialPoint() {
		return initialPoint;
	}

	public void setInitialPoint(String initialPoint) {
		this.initialPoint = initialPoint;
	}

	public List<Long> getrIds() {
		return rIds;
	}

	public void setrIds(List<Long> rIds) {
		this.rIds = rIds;
	}
	
	public Date getCreatedDate(){
		return createdDate;
	}
	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public List<Ride> getRidesAfterDate(List<Ride> listRides, Date date) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		List<Ride> newList = new ArrayList<>();
		for(int i = 0; i < listRides.size(); i ++) {
			Ride ride = listRides.get(i);
			Date rideDate = formatter.parse(ride.getDate());
			if(rideDate.after(date)){
				newList.add(ride);
			}
		}
		
		return newList;
	}
	public List<Ride> sortRidesByDate(List<Ride> rides) throws ParseException{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		//List<Ride> sortedRides = new ArrayList<>();
		for(int i = 0; i < rides.size()-1; i ++){
			for(int j = 0; j < rides.size()-1; j ++){
				Ride ride = rides.get(j);
				Ride nextRide = rides.get(j+1);
				Date date = formatter.parse(ride.getDate());
				Date nextDate = formatter.parse(nextRide.getDate());
				if(date.after(nextDate)){
					rides.set(j, nextRide);
					rides.set(j+1, ride);
				}
				else if(date.equals(nextDate)){
					if(ride.getDepartTime().compareTo(nextRide.getDepartTime()) > 0){
						rides.set(j, nextRide);
						rides.set(j+1, ride);
					}
				}
			}
		}
		return rides;		
	}
	
	public boolean checkIfUserInRide(User user){
		if(this.rIds.contains(user.getId()) || this.dId == user.getId()){
			return true;
		}
		return false;
	}
}
