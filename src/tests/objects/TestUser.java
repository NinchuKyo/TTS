package tests.objects;

import junit.framework.TestCase;

import tts.objects.User;

public class TestUser extends TestCase
{
	private User newUser;
	
	public TestUser(String arg0)
	{
		super(arg0);
		
		System.out.println("\nStarting test User");
	}
	
	public void testNull()
	{
		System.out.println("\nStarting testNull: users");
		
		try
		{
			new User(null);
			fail("Illegal Argument Exception expected");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testNull: users");
	}
	
	public void testEmptyString()
	{
		System.out.println("\nStarting testEmptyString: users");
		
		try
		{
			new User("");
			fail("Illegal Argument Exception expected");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testEmptyString: users");
	}
	
	public void testMultipleUsers()
	{
		System.out.println("\nStarting testMultipleUsers: users");
		
		int userId1;
		int userId2;
		int userId3;
		
		newUser = new User("JoeSmith");
		userId1 = newUser.getUserID();
		assertNotNull(newUser);
		assertEquals(newUser.getUserName(), "JoeSmith");
		assertTrue(newUser.getUserID() == userId1);
		
		newUser = new User("JaneSmith");
		userId2 = newUser.getUserID();
		assertNotNull(newUser);
		assertEquals(newUser.getUserName(), "JaneSmith");
		assertTrue(newUser.getUserID() == userId1 + 1);
		
		newUser = new User("JaneDoe");
		userId3 = newUser.getUserID();
		assertNotNull(newUser);
		assertEquals(newUser.getUserName(), "JaneDoe");
		assertTrue(newUser.getUserID() == userId2 + 1);
		
		assertTrue(userId1 != userId2);
		assertTrue(userId1 != userId3);
		assertTrue(userId2 != userId3);
		
		System.out.println("Finished testMultipleUsers: users");
	}
	
	public void testUserId()
	{
		System.out.println("\nStarting testUserId: users");
		
		User newUser = new User("Billy");
		assertTrue(newUser.getUserID() > 0);
		
		User newOtherUser = new User("Bob");
		assertTrue(newOtherUser.getUserID() == newUser.getUserID() + 1);
		
		newUser = new User("Jim");
		assertTrue(newUser.getUserID() == newOtherUser.getUserID() + 1);
		
		for (int i = 0; i < 1000000; i++)
		{
			newUser = new User("Abc");
		}
		
		assertTrue(newUser.getUserID() == newOtherUser.getUserID() + 1000001);
		
		System.out.println("Finished testUserId: users");
	}
	
	public void testIllegalCharacters()
	{
		System.out.println("\nStarting testIllegalCharacters: users");
		
		try
		{
			new User("RegularName");
		}
		catch (IllegalArgumentException iae)
		{
			fail("iae not expected.");
		}
		
		try
		{
			new User("NumbersWithinName3350");
		}
		catch (IllegalArgumentException iae)
		{
			fail("iae not expected.");
		}
		
		try
		{
			new User("#BestUserNameEver");
			fail("iae expected.");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			new User("Hello World");
			fail("iae expected.");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			new User("@!#$%^&*(&^%$&*(^%$&%$");
			fail("iae expected.");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			User newUser = new User("ThisIsAValidUserName");
			newUser.setUserName("#This&ISNOTAVALID");
			fail("iae expected.");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testIllegalCharacters: users");
	}
	
	public void testLongUserNames()
	{
		System.out.println("\nStarting testLongUserNames: users");
		
		try
		{
			new User("ThisIsNotAValidUserNameBecauseTheLengthIsGreaterThan32");
			fail("iae expected.");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			User newUser = new User("ThisIsAValidUserNameLength");
			newUser.setUserName("ThisIsNowNotAValidUserLengthBecauseTheLengthHasBeenIncreasedToMoreThan32");
			fail("iae expected.");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testLongUserNames: users");
	}
	
	public void testUserComparison()
	{
		System.out.println("\nStarting testUserComparison: users");
		
		User newUser1 = new User("NewUser1");
		User newUser2 = new User("NewUser2");
		
		// test username comparison
		assertFalse(newUser1.getUserName().equals(newUser2.getUserName()));
		assertFalse(newUser1.equals(newUser2));
		assertFalse(newUser1.getUserID() == newUser2.getUserID());
		
		newUser2 = newUser1;
		assertTrue(newUser1.equals(newUser2));
		assertTrue(newUser1.getUserName().equals(newUser2.getUserName()));
		assertTrue(newUser1.getUserID() == newUser2.getUserID());
		
		System.out.println("Finished testUserComparison: users");
	}
	
}
