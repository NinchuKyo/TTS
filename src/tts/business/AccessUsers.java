package tts.business;

import java.util.List;

import tts.application.*;
import tts.objects.User;
import tts.persistence.DataAccess;

public class AccessUsers
{
	private DataAccess dataAccess;
	private List<User> users;
	private static User loggedInUser = null;
	
	public AccessUsers()
	{
		dataAccess = (DataAccess) Services.getDataAccess();
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
	
	public boolean addUser(User toAdd)
	{
		boolean inserted = false;
		
		if (toAdd != null)
		{
			if (exists(toAdd) == false)
			{
				users.add(toAdd);
				inserted = dataAccess.insertUser(toAdd);
			}
		}
		else
		{
			throw new IllegalArgumentException("Cannot add null!");
		}
		
		return inserted;
	}
	
	public static User getLoggedInUser()
	{
		return loggedInUser;
	}
	
	public void setLoggedInUser(String userName)
	{
		if(userName != null && userName.isEmpty() == false)
		{
			for (User u : users)
			{
				if (u.getUserName().equals(userName))
				{
					loggedInUser = u;
					break;
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("Cannot set logged in user to null!");
		}
	}
	
	public boolean exists(User toFind)
	{
		boolean exists = false;
		
		for (User u : users)
		{
			if (u.equals(toFind))
				exists = true;
		}
		
		return exists;
	}
}
