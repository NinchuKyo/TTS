package tts.presentation;

import java.util.List;

import tts.business.AccessUsers;
import tts.objects.*;

public class CreateTaskWindow extends TaskWindow
{	
	public CreateTaskWindow()
	{
		super("Create a task");
	}
	
	public void setDropDownValues()
	{
		List<User> theUsers = users.getUsers();
		for (User u : theUsers)
		{
			cboxAssignedTo.add(u.getUserName());
		}
		cboxAssignedTo.select(cboxAssignedTo.indexOf(AccessUsers
				.getLoggedInUser().getUserName()));
		
		if (StatusCode.values().length > 0)
		{
			cboxStatus.setItems(StatusCode.enumToStringArray());
			cboxStatus.select(0);
		}
		
		if (PriorityCode.values().length > 0)
		{
			cboxPriority.setItems(PriorityCode.enumToStringArray());
			cboxPriority.select(0);
		}
	}
}
