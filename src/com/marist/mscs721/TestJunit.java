package com.marist.mscs721;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit {
	
   String message;	
   MessageUtil messageUtil = new MessageUtil(message);
   /*RoomScheduler rs = new RoomScheduler();
   int value = rs.mainMenu();*/
 
   
   @Test
   public void testPrintMessage() {	  
      assertEquals(message,messageUtil.printMessage());
   }
}