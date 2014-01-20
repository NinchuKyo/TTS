package tests.persistence;

import junit.framework.TestCase;
import java.util.List;

import tts.application.Services;
import tts.persistence.DataAccess;
import tts.objects.User;
import tts.objects.Task;

public class TestDataAccessStub extends TestCase
{
	private static DataAccess testDatabase;
	private static List<User> databaseUsers;
	private static List<Task> databaseTasks;
	private static User newUser;
	private static Task newTask1, newTask2, newTask3;
	
	public TestDataAccessStub(String arg0)
	{
		super(arg0);
		
		System.out.println("\nStarting Persistence test DataAccess");
	}
	
	public void testStub()
	{
		Services.closeDataAccess();
		testDatabase = (DataAccess) Services
				.createDataAccess(new DataAccessStub());
		runAllTests();
	}
	
	public static void allTests()
	{
		testDatabase = Services.getDataAccess();
		runAllTests();
	}
	
	private static void runAllTests()
	{
		databaseInitTest();
		duplicateInsertTest();
		usersTest();
		tasksTest();
	}
	
	public static void databaseInitTest()
	{
		System.out.println("\nStarting testDatabaseInit");
		
		assertNotNull(testDatabase);
		testDatabase.open();
		
		assertNotNull(testDatabase.getTasksSequential());
		assertNotNull(testDatabase.getUsersSequential());
		
		System.out.println("Finished testDatabaseInit");
	}
	
	public static void duplicateInsertTest()
	{
		System.out.println("\nStarting testDuplicateInsert");
		boolean inserted;
		int size;
		
		databaseUsers = testDatabase.getUsersSequential();
		size = databaseUsers.size();
		
		newUser = new User("SameUser");
		inserted = testDatabase.insertUser(newUser);
		assertTrue(inserted);
		inserted = testDatabase.insertUser(newUser);
		assertFalse(inserted);
		inserted = testDatabase.insertUser(newUser);
		assertFalse(inserted);
		
		databaseUsers = testDatabase.getUsersSequential();
		
		assertTrue(size + 1 == databaseUsers.size());
		assertEquals(testDatabase.getUsersSequential().get(size).getUserName(),
				"SameUser");
		
		databaseTasks = testDatabase.getTasksSequential();
		size = databaseTasks.size();
		
		newTask1 = new Task("same task", newUser, newUser);
		
		inserted = testDatabase.insertTask(newTask1);
		assertTrue(inserted);
		inserted = testDatabase.insertTask(newTask1);
		assertFalse(inserted);
		inserted = testDatabase.insertTask(newTask1);
		assertFalse(inserted);
		
		databaseTasks = testDatabase.getTasksSequential();
		
		assertTrue(databaseTasks.size() == size + 1);
		assertEquals(databaseTasks.get(size).getTitle(), "same task");
		
		System.out.println("Finished testDuplicateInsert");
	}
	
	public static void usersTest()
	{
		System.out.println("\nStarting testUserInsert");
		
		newUser = new User("TestUserA");
		testDatabase.insertUser(newUser);
		newUser = new User("TestUserB");
		testDatabase.insertUser(newUser);
		newUser = new User("TestUserC");
		testDatabase.insertUser(newUser);
		
		databaseUsers = testDatabase.getUsersSequential();
		
		assertEquals(databaseUsers.get(databaseUsers.size()-3).getUserName(), "TestUserA");
		assertEquals(databaseUsers.get(databaseUsers.size()-2).getUserName(), "TestUserB");
		assertEquals(databaseUsers.get(databaseUsers.size()-1).getUserName(), "TestUserC");
		
		System.out.println("Finished testUserInsert");
	}
	
	public static void tasksTest()
	{
		System.out.println("\nStarting testTasks");
		
		boolean updated;
		boolean deleted;
		int size;
		
		newUser = new User("TestUserA");
		
		newTask1 = new Task("Finish assignment A", newUser, newUser);
		testDatabase.insertTask(newTask1);
		newTask2 = new Task("Finish assignment B", newUser, newUser);
		testDatabase.insertTask(newTask2);
		newTask3 = new Task("Finish assignment C", newUser, newUser);
		testDatabase.insertTask(newTask3);
		
		databaseTasks = testDatabase.getTasksSequential();
		
		assertEquals(databaseTasks.get(databaseTasks.size()-3).getTitle(), newTask1.getTitle());
		assertEquals(databaseTasks.get(databaseTasks.size()-2).getTitle(), newTask2.getTitle());
		assertEquals(databaseTasks.get(databaseTasks.size()-1).getTitle(), newTask3.getTitle());
		
		// test updating tasks
		newTask1.setTitle("I actually want to finish assignment D");
		updated = testDatabase.updateTask(newTask1);
		assertTrue(updated);
		
		databaseTasks = testDatabase.getTasksSequential();
		
		// test if the task objects still remain the same
		assertEquals("I actually want to finish assignment D", databaseTasks
				.get(databaseTasks.indexOf(newTask1)).getTitle());
		
		size = databaseTasks.size();
		
		// test deleting tasks
		deleted = testDatabase.deleteTask(newTask1);
		assertTrue(deleted);
		
		deleted = testDatabase.deleteTask(newTask3);
		assertTrue(deleted);
		
		databaseTasks = testDatabase.getTasksSequential();
		
		assertTrue(databaseTasks.size() == size-2);
		assertEquals(databaseTasks.get(databaseTasks.size()-1).getTitle(), "Finish assignment B");
		
		deleted = testDatabase.deleteTask(newTask2);
		assertTrue(deleted);
		
		// test deleted all tasks
		databaseTasks = testDatabase.getTasksSequential();
		assertTrue(databaseTasks.size() == size-3);
		
		System.out.println("Finished testTasks");
	}
	
}