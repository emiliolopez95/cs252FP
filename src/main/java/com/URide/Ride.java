package com.URide;

import java.util.*;


public class Ride {
	
	private Long id;
	
	private Long dId;
	private List<Long> rIds;
	
	private String date;
	
	private String arriveTime;
	private String departTime;
	private String finalPoint;
	private String initialPoint;
	
	private Date createdDate;
	
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
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
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

	public void setDestinationPoint(String destinationPoint) {
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
	
	public void setCretedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
