package tts.objects;

public class User
{
	private String userName;
	private int userID;
	private static int nextUserID = 1;
	public final static int MAX_NAME_LENGTH = 32;
	
	public User(String userName)
	{
		setUserName(userName);
		setUserID(nextUserID);
		nextUserID++;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public void setUserName(String userName)
	{
		if (userName == null || userName.length() == 0
				|| hasIllegalCharacters(userName)
				|| userName.length() > MAX_NAME_LENGTH)
		{
			throw new IllegalArgumentException(
					"User name cannot be empty, null, longer than 32 characters or have illegal characters!");
		}
		
		this.userName = userName;
	}
	
	public int getUserID()
	{
		return userID;
	}
	
	public void setUserID(int userID)
	{
		if (userID < 1)
		{
			throw new IllegalArgumentException("User ID cannot be less than 1!");
		}
		this.userID = userID;
	}
	
	public static void setNextUserID(int staticUserID)
	{
		if (staticUserID < 1)
		{
			throw new IllegalArgumentException("User ID cannot be less than 1!");
		}
		nextUserID = staticUserID;
	}
	
	private boolean hasIllegalCharacters(String name)
	{
		boolean illegalChars = false;
		final String LEGAL_CHARACTERS = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		
		for (int i = 0; i < name.length(); i++)
		{
			if (LEGAL_CHARACTERS.indexOf(name.charAt(i)) < 0)
			{
				illegalChars = true;
			}
		}
		
		return illegalChars;
	}
	
	public boolean equals(User user)
	{
		return (this.userID == user.getUserID() || this.userName.equals(user.getUserName()));
	}
	
	public boolean equals(String userName)
	{
		return (this.userName.equals(userName));
	}
	
	public String toString()
	{
		return "User Name: " + userName;
	}
}