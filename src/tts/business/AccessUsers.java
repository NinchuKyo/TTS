package tts.business;

import java.util.List;

import tts.application.*;
import tts.objects.User;
import tts.persistence.DataAccessStub;

public class AccessUsers
{
	private DataAccessStub dataAccess;
	private List<User> users;
	private static User loggedInUser = null;
	
	public AccessUsers()
	{
		dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
		users = null;
	}
	
	public List<User> getUsers()
	{
		if (users == null)
		{
			users = dataAccess.getUsersSequential();
		}
		
		return users;
	}
	
	public static User getLoggedInUser()
	{
		return loggedInUser;
	}
	
	public void setLoggedInUser(User user)
	{
		loggedInUser = user;
	}
	
	public void setLoggedInUser(String userName)
	{
		for (User u : users)
		{
			if (u.getUserName().equals(userName))
			{
				loggedInUser = u;
			}
		}
	}
}
