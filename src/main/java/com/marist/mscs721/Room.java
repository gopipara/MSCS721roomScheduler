/**
   * Gopi License
   * © 2016 ALL RIGHTS RESERVED
   */
package main.java.com.marist.mscs721;

import java.util.ArrayList;

public class Room {

	private String name;
	private int capacity;
	private String building;
	private String location;
	private ArrayList<Meeting> meetings;


	public Room(String newName, int newCapacity, String building, String location) {
		setName(newName);
		setCapacity(newCapacity);
		setBuilding(building);
		setLocation(location);
		setMeetings(new ArrayList<Meeting>());
	}

	public void addMeeting(Meeting newMeeting) {
		this.getMeetings().add(newMeeting);
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getBuilding() {
		return building;
	}


	public void setBuilding(String building) {
		this.building = building;
	}

	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}



	public int getCapacity() {
		return capacity;
	}


	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}


	public ArrayList<Meeting> getMeetings() {
		return meetings;
	}


	public void setMeetings(ArrayList<Meeting> meetings) {
		this.meetings = meetings;
	}

}
