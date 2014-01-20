package tests.persistence;

import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.List;

import tts.persistence.DataAccessStub;
import tts.objects.User;
import tts.objects.Task;

public class TestDataAccessStub extends TestCase
{
	DataAccessStub testDatabase;
	List<User> testListUsers;
	List<Task> testListTasks;
	
	public TestDataAccessStub(String arg0)
	{
		super(arg0);
	}
	
	private void setUpData()
	{
		testDatabase = new DataAccessStub("Test Database");
		testListUsers = new ArrayList<User>();
		testListTasks = new ArrayList<Task>();
	}
	
	public void testDatabaseInit()
	{
		System.out.println("\nStarting testDatabaseInit");
		
		assertTrue(testDatabase == null);
		testDatabase = new DataAccessStub("Test Database");
		testDatabase.open("Test Database");
		assertTrue(testDatabase != null);
		
		System.out.println("Finished testDatabaseInit");
	}
	
	public void testDuplicateUserInsert()
	{
		System.out.println("\nStarting testDuplicateUserInsert");
		
		setUpData();
		
		User newUser = new User("SameUser");
		testDatabase.insertUser(newUser);
		testDatabase.insertUser(newUser);
		testDatabase.insertUser(newUser);
		
		assertTrue(testDatabase.getUsersSequential().size() != 3);
		assertTrue(testDatabase.getUsersSequential().get(0).equals(newUser));
		
		System.out.println("Finished testDuplicateUserInsert");
	}
	
	public void testUserInsert()
	{
		System.out.println("\nStarting testUserInsert");
		
		setUpData();
		
		User newUser;
		List<User> databaseUsers;
		
		newUser = new User("TestUserA");
		testListUsers.add(newUser);
		
		newUser = new User("TestUserB");
		testListUsers.add(newUser);
		
		newUser = new User("TestUserC");
		testListUsers.add(newUser);
		
		for (User u : testListUsers)
		{
			testDatabase.insertUser(u);
		}
		
		databaseUsers = testDatabase.getUsersSequential();
		
		assertTrue(databaseUsers.get(0).getUserName().equals("TestUserA"));
		assertTrue(databaseUsers.get(1).getUserName().equals("TestUserB"));
		assertTrue(databaseUsers.get(2).getUserName().equals("TestUserC"));
		
		assertTrue(databaseUsers.get(0).equals(testListUsers.get(0)));
		assertTrue(databaseUsers.get(1).equals(testListUsers.get(1)));
		assertTrue(databaseUsers.get(2).equals(testListUsers.get(2)));
		
		assertTrue(databaseUsers.equals(testListUsers));
		
		System.out.println("Finished testUserInsert");
	}
	
	public void testDuplicateTaskInsert()
	{
		System.out.println("\nStarting testDuplicateTaskInsert");
		
		setUpData();
		User newUser = new User("SomeUser");
		Task newTask = new Task("This is the same task being entered", newUser,
				newUser);
		
		testDatabase.insertTask(newTask);
		testDatabase.insertTask(newTask);
		testDatabase.insertTask(newTask);
		
		assertTrue(testDatabase.getTasksSequential().size() != 3);
		assertTrue(testDatabase.getTasksSequential().get(0).equals(newTask));
		
		System.out.println("Finished testDuplicateTaskInsert");
	}
	
	public void testTasks()
	{
		System.out.println("\nStarting testTasks");
		
		setUpData();
		
		User newUser;
		Task newTask;
		Task editTask;
		List<Task> databaseTasks;
		
		newUser = new User("TestUserA");
		
		newTask = new Task("Finish assignment A", newUser, newUser);
		testListTasks.add(newTask);
		
		newTask = new Task("Finish assignment B", newUser, newUser);
		testListTasks.add(newTask);
		
		newTask = new Task("Finish assignment C", newUser, newUser);
		testListTasks.add(newTask);
		
		for (Task t : testListTasks)
		{
			testDatabase.insertTask(t);
		}
		
		databaseTasks = testDatabase.getTasksSequential();
		
		assertTrue(testListTasks.get(0).equals(databaseTasks.get(0)));
		assertTrue(testListTasks.get(1).equals(databaseTasks.get(1)));
		assertTrue(testListTasks.get(2).equals(databaseTasks.get(2)));
		
		assertTrue(testListTasks.equals(databaseTasks));
		
		// test updating tasks
		editTask = testListTasks.get(1);
		editTask.setTitle("I actually want to finish assignment D");
		testDatabase.updateTask(editTask);
		
		databaseTasks = testDatabase.getTasksSequential();
		
		// test if the task objects still remain the same
		assertTrue(testListTasks.get(1).equals(editTask));
		assertTrue(testListTasks.get(1).equals(databaseTasks.get(1)));
		assertTrue(editTask.equals(databaseTasks.get(1)));
		assertTrue(databaseTasks.get(1).getTitle()
				.equals("I actually want to finish assignment D"));
		
		// test deleting tasks
		editTask = testListTasks.get(0);
		testDatabase.deleteTask(editTask);
		
		editTask = testListTasks.get(2);
		testDatabase.deleteTask(editTask);
		
		databaseTasks = testDatabase.getTasksSequential();
		
		assertTrue(databaseTasks.size() == 1);
		assertTrue(databaseTasks.get(0).equals(testListTasks.get(1)));
		assertTrue(databaseTasks.get(0).getTitle()
				.equals("I actually want to finish assignment D"));
		
		editTask = databaseTasks.get(0);
		testDatabase.deleteTask(editTask);
		
		// test deleted all tasks
		databaseTasks = testDatabase.getTasksSequential();
		assertTrue(databaseTasks.size() == 0);
		
		System.out.println("Finished testTasks");
	}
	
}