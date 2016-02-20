package com.marist.mscs721;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RoomScheduler {
	protected static Scanner keyboard = new Scanner(System.in);


	public static void main(String[] args) 
	{
		
		Boolean end = false;
		ArrayList<Room> rooms = new ArrayList<Room>();

		while (!end) 
		{
			switch (mainMenu()) 
			{

			case 1:
				addRoom(rooms);
				break;
			case 2:
				System.out.println(removeRoom(rooms));
				break;
			case 3:
				System.out.println(scheduleRoom(rooms));
				break;
			case 4:
				System.out.println(listSchedule(rooms));
				break;
			case 5:
				System.out.println(listRooms(rooms));
				break;
			case 6:
				exportRooms(rooms);
				break;
			case 7:
				exportSchedule(rooms);
				break;
			default:
				System.out.println("Please enter between (1 - 7)");
				
			}

		}

	}

	
	private static void exportRooms(ArrayList<Room> roomList) 
	{
		
		
		JSONArray roomObj = new JSONArray();
	
			for (Room room : roomList) 
				{
					JSONObject singleObj = new JSONObject();
					singleObj.put("roomName", room.getName());
					singleObj.put("capacity", room.getCapacity());
					roomObj.add(singleObj);
					
					
				}
		
		
			FileWriter file;
			try {
				file = new FileWriter("rooms.json");
				file.write(roomObj.toJSONString());
				System.out.println("Successfully Copied JSON Object to File...");
				System.out.println("\nJSON Object: " + roomObj);
				file.flush();
				file.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
		
	}

	@SuppressWarnings("unchecked")
	private static void exportSchedule(ArrayList<Room> roomList) {
		
		
		
		
		JSONArray scheduleObj = new JSONArray();
		JSONObject singleRoomObj = new JSONObject();
		for (Room room : roomList) 
		{
			//System.out.println(room.getName());
			
			JSONArray meetingArrayObj = new JSONArray();
		 for (Meeting m : room.getMeetings()) 
		 {
			 	JSONObject singleObj = new JSONObject();
			 	
				singleObj.put("start", m.getStartTime().toString());
				singleObj.put("stop", m.getStopTime().toString());
				//System.out.println(singleObj);
				meetingArrayObj.add(singleObj);
		 }
		 
		 singleRoomObj.put(room.getName(), meetingArrayObj);
		 //System.out.println(singleRoomObj);
		 
		}
		
		scheduleObj.add(singleRoomObj);
		
		FileWriter file;
		try {
			file = new FileWriter("schedule.json");
			file.write(scheduleObj.toJSONString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + scheduleObj);
			file.flush();
			file.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


	/**
	 * This method assigns the room list into the array.
	 * @param roomList
	 * @return schedule for rooms and the rooms available,if not equal to -1
	 *-1(indicates that there is no room and if there is no schedule for the available room.) 
	 */
	public static String listSchedule(ArrayList<Room> roomList) 
	 
	{
		
		if(roomList.size()>0)
		{
			String roomName = getRoomName();
			if(findRoomIndex(roomList,roomName) != -1)
			{
			    System.out.println(roomName + " Schedule");
				System.out.println("---------------------");
				
				 if(getRoomFromName(roomList, roomName).getMeetings().size() >0)
				 {
					 for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) 
					 {
							System.out.println(m.toString());
					 }
				 }
				 else
				 {
					 System.out.println("No Schedule for the requested room: "+roomName);
				 }
				
			}
			else
			{
				return "Please check room no : "+roomName;
			}
			
			}
		else
		{
			return "No Schedule to show !!";
		}
		
		return "";
		}

	/**
	 * This method provides the main menu for scheduling a room by the values given from the keyboard.
	 */
	protected static int mainMenu() 
	{
		int option = 0;
		System.out.println("Main Menu:");
		System.out.println("  1 - Add a room");
		System.out.println("  2 - Remove a room");
		System.out.println("  3 - Schedule a room");
		System.out.println("  4 - List Schedule");
		System.out.println("  5 - List Rooms");
		System.out.println("  6 - Export Rooms");
		System.out.println("  7 - Export Room Scheduling");
		System.out.println("Enter your selection: ");
			
		try {
			option = keyboard.nextInt();
			
		} catch (Exception e) {
			System.out.println("Error: Input Mismatch");
            keyboard.next();
			
		}
		
		return option;
	}

	/**
	 * This method adds the rooms to the room list.
	 * @param   string  
	 */
	protected static void addRoom(ArrayList<Room> roomList) 
	{
		System.out.println("Add a room:");
		String name = getRoomName();
		System.out.println("Room capacity?");
		try {
			int capacity = keyboard.nextInt();

			Room newRoom = new Room(name, capacity);
			roomList.add(newRoom);

			System.out.println("Room '" + newRoom.getName() + "' added successfully!");
		} catch (Exception e) {
			System.out.println("Error: Input Mismatch-  Capacity must be an Integer");
            keyboard.next();
			
		}
		
	}

	/**
	 * This method removes the room from the room list.
	 * @param string <> roomList into an array list
	 * @return "Room removed successfully!"
	 */
	protected static String removeRoom(ArrayList<Room> roomList) 
	{
		System.out.println("Remove a room:");
		
		int roomIndex = findRoomIndex(roomList, getRoomName());
		// i have found an error in the given code here. This method is removing the rooms which are not even present in the room list.
		if(roomIndex>=0)
			roomList.remove(roomIndex);
		else
			System.out.println("ERROR:Please check room no");
		
		return "Room removed successfully";
	}

	/**
	 * This method is used to list the rooms available in the room list.
	 * @param string <>list rooms from the array list.
	 * @return Available rooms in the Room list
	 */
	protected static String listRooms(ArrayList<Room> roomList) 
	{
		System.out.println("Room Name - Capacity");
		System.out.println("---------------------");

		for (Room room : roomList) 
		{
			System.out.println(room.getName() + " - " + room.getCapacity());
		}

		System.out.println("---------------------");

		return roomList.size() + " Room(s)";
	}

	/**
	 * This method is used to schedule a room if the room is available and if there is no schedule for the available room.
	 * @param roomList
	 * @return <>scheduling a room.
	 */
	protected static String scheduleRoom(ArrayList<Room> roomList) 
	{
		if(roomList.size()>0)
		
			// Index size()>0 mean room is available in the array list.	and we can schedule a room.	
		
		{
	
		System.out.println("Schedule a room:");
		String name = getRoomName();
		if(findRoomIndex(roomList,name) != -1)
			
			// i have considered index as -1, if there is no room or there is no schedule for the available room.
		   //  program enters in to this loop if index is not equals to -1
		
		{
			System.out.println("Start Date? (yyyy-mm-dd):");
			String startDate = keyboard.next();
			System.out.println("Start Time?(hh:mm:ss)");
			String startTime = keyboard.next();
		    System.out.println("End Date? (yyyy-mm-dd):");
		    String endDate = keyboard.next();
			System.out.println("End Time? (hh:mm:ss)");
			String endTime = keyboard.next();
		
				try 
				{
					Timestamp startTimestamp = Timestamp.valueOf(startDate + " " + startTime);
					Timestamp endTimestamp = Timestamp.valueOf(endDate + " " + endTime);

					//System.out.println("Subject:");
					String subject = keyboard.nextLine();
					
					Room curRoom = getRoomFromName(roomList, name);

					Meeting meeting = new Meeting(startTimestamp, endTimestamp, subject);

					//check conflict
					if(checkConflict(roomList,name,startTimestamp, endTimestamp))
					{
						return "Time Conflict in Scheduling";
					}
					else
					{
						curRoom.addMeeting(meeting);
						return "Meeting Scheduled Succesfully";
					}
				
				}
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return "Please check Date/Time format!";
				}
			}
		        else
		        {
				return " Room '"+name+"' Not Avialble. \n Sorry"; // if the room name is not available in the array list.
			    }
		
		}
		else
		{
			return "No Rooms Avialable to Schedule"; // if the index is -1.
		}
		
	}

	/**
	 * This method is used to check the conflict for scheduling a room. 
	 * @param roomList <> room list from the array list
	 * @param roomName <> gets the room name from the array list based on the index.
	 * @param start    <> checks the start of the given room 
	 * @param end      <> checks the end time of the given room
	 * @return         <> *** Schedule conflict ***" ,  . 
	 *                 <>"Successfully scheduled meeting!" if there is no conflict.
	 */
	protected static boolean checkConflict(ArrayList<Room> roomList, String roomName,Timestamp start,Timestamp end) 
	{
		
		boolean isConflict = false;
		
		
		for (Meeting m : getRoomFromName(roomList, roomName).getMeetings()) 
		{
			System.out.println(start);
			System.out.println(m.getStartTime());
			if( start.equals(m.getStartTime()))
			{
				isConflict = true;
				break;
			}
			else if(start.after(m.getStartTime()) && start.before(m.getStopTime()))
			{
				isConflict = true;
				break;
			}
			else if(end.after(m.getStartTime()) && end.before(m.getStopTime()))
			{
				isConflict = true;
				break;
			}
			
		}
		
		return isConflict;
	
	}
	
	
	protected static Room getRoomFromName(ArrayList<Room> roomList, String name) 
	{
		
		return roomList.get(findRoomIndex(roomList, name));
	}
	
	   
	/**
	 * This function returns room index if the given room found in the roomList else returns -1.
	 * @param roomList  <> room index in the array list
	 * @param roomName  <> room name which is stored in the array list.
	 * @return          <> -1 if we have no rooms
	 */
	
	   protected static int findRoomIndex(ArrayList<Room> roomList, String roomName) 
	{
		int roomIndex = 0;
		boolean isFound = false;
		if(roomList.isEmpty())
		{
			roomIndex = -1; // -1 implies empty room 
		}
		else
		{
			for (Room room : roomList) 
			{
				
				if (room.getName().compareTo(roomName) == 0) 
				{
					isFound = true;
					break;
				}
					roomIndex++;
			}
		}
		
		if(isFound)
			return roomIndex;
		else
			return -1;
	}

	/**
	 * This method is used check the schedule 
	 * @return <> "Room Name?"
	 */
	   protected static String getRoomName() 
	{
		System.out.println("Room Name:");
		return keyboard.next();
	}

}