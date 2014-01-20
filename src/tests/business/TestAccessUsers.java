package tests.business;

import java.util.List;

import tts.application.Main;
import tts.application.Services;
import tts.business.AccessUsers;
import tts.objects.User;
import junit.framework.TestCase;

public class TestAccessUsers extends TestCase
{
	private AccessUsers userAccess;
	private List<User> users;
	private User user1, user2;
	
	public TestAccessUsers(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(Main.dbName);
	}
	
	public void testAccessUsers()
	{
		System.out.println("\nStarting testAccessUsers");
		
		userAccess = new AccessUsers();
		assertNotNull(userAccess);
		
		users = userAccess.getUsers();
		assertNotNull(users);
		
		user1 = users.get(0);
		user2 = users.get(1);
		
		assertEquals(user1.getUserName(), "StudentInCOMP3350");
		assertEquals(user2.getUserName(), "JamesBond");
		
		userAccess.setLoggedInUser(user1);
		assertTrue(AccessUsers.getLoggedInUser().getUserName()
				.equals(user1.getUserName()));
		userAccess.setLoggedInUser(user2);
		assertTrue(AccessUsers.getLoggedInUser().getUserName()
				.equals(user2.getUserName()));
		
		System.out.println("Finished testAccessUsers");
	}
}
