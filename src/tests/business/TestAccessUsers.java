package tests.business;

import java.util.List;

import tests.persistence.DataAccessStub;
import tts.application.Services;
import tts.business.AccessUsers;
import tts.objects.User;
import junit.framework.TestCase;

public class TestAccessUsers extends TestCase
{
	private AccessUsers userAccess;
	private List<User> users;
	private User newUser1, newUser2, newUser3, newUser4, newUser5;
	private int count;
	private boolean inserted;
	
	public TestAccessUsers(String arg0)
	{
		super(arg0);
		
		Services.closeDataAccess();
		System.out.println("\nStarting test AccessUsers");
		Services.createDataAccess(new DataAccessStub());
		
		newUser1 = new User("Bob");
		newUser2 = new User("Bill");
		newUser3 = new User("John");
		newUser4 = new User("Jane");
		newUser5 = new User("Dave");
		
		userAccess = new AccessUsers();
	}
	
	public void setUp()
	{
	}
	
	public void testNull()
	{
		System.out.println("\nStarting testNull: user functions");
		
		assertNotNull(userAccess);
		
		try
		{
			userAccess.addUser(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			userAccess.setLoggedInUser(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testNull");
	}
	
	public void testInitialSetup()
	{
		System.out.println("\nStarting testInitialSetup");
		
		assertNotNull(userAccess);
		
		users = userAccess.getUsers();
		assertNotNull(users);
		
		User user1 = users.get(0);
		User user2 = users.get(1);
		
		assertEquals(user1.getUserName(), "StudentInCOMP3350");
		assertEquals(user2.getUserName(), "JamesBond");
		
		System.out.println("Finished testInitialSetup");
	}
	
	public void testOneUser()
	{
		System.out.println("\nStarting testOneUser");
		
		users = userAccess.getUsers();
		count = users.size();
		
		assertFalse(userAccess.exists(newUser1));
		inserted = userAccess.addUser(newUser1);
		assertTrue(inserted);
		assertTrue(userAccess.exists(newUser1));
		
		users = userAccess.getUsers();
		
		assertEquals(users.size(), count + 1);
		assertEquals("Bob", users.get(users.size() - 1).getUserName());
		
		userAccess.setLoggedInUser(newUser1.getUserName());
		assertEquals(AccessUsers.getLoggedInUser().getUserName(), "Bob");
		
		System.out.println("Finished testOneUser");
	}
	
	public void testMultipleUsers()
	{
		System.out.println("\nStarting testMultipleUsers");
		
		users = userAccess.getUsers();
		count = users.size();
		
		assertFalse(userAccess.exists(newUser2));
		inserted = userAccess.addUser(newUser2);
		assertTrue(inserted);
		assertTrue(userAccess.exists(newUser2));
		
		assertFalse(userAccess.exists(newUser3));
		inserted = userAccess.addUser(newUser3);
		assertTrue(inserted);
		assertTrue(userAccess.exists(newUser3));
		
		assertFalse(userAccess.exists(newUser4));
		inserted = userAccess.addUser(newUser4);
		assertTrue(inserted);
		assertTrue(userAccess.exists(newUser4));
		
		assertFalse(userAccess.exists(newUser5));
		inserted = userAccess.addUser(newUser5);
		assertTrue(inserted);
		assertTrue(userAccess.exists(newUser5));
		
		users = userAccess.getUsers();
		
		assertEquals(users.size(), count + 4);
		assertEquals("Dave", users.get(users.size() - 1).getUserName());
		
		userAccess.setLoggedInUser(newUser5.getUserName());
		assertEquals(AccessUsers.getLoggedInUser().getUserName(), "Dave");
		userAccess.setLoggedInUser(newUser4.getUserName());
		assertEquals(AccessUsers.getLoggedInUser().getUserName(), "Jane");
		
		System.out.println("Finished testMultipleUsers");
	}
}
