package main.java.com.marist.mscs721;
/*
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({ 
	RoomSchedulerTest.class
})
public class TestSuite {
}*/

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestSuite {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(Testcase1.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}