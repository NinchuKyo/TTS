package tts.presentation;

import java.util.Calendar;
import java.util.List;

import tts.business.AccessTasks;
import tts.business.AccessUsers;
import tts.business.FormatDate;
import tts.objects.*;

import acceptanceTests.Register;

public class CreateTaskWindow extends TaskWindow
{
	public CreateTaskWindow()
	{
		super("Create a task");
		Register.newWindow(this);
	}
	
	public void setTextFieldValues()
	{
		List<User> theUsers = users.getUsers();
		StaticWindowMethods
				.populateAssignedToDropDown(cboxAssignedTo, theUsers);
		cboxAssignedTo.select(cboxAssignedTo.indexOf(AccessUsers
				.getLoggedInUser().getUserName()));
		
		if (StatusCode.values().length > 0)
		{
			StaticWindowMethods.populateStatusDropDown(cboxStatus);
			cboxStatus.select(0);
		}
		
		if (PriorityCode.values().length > 0)
		{
			StaticWindowMethods.populatePriorityDropDown(cboxPriority);
			cboxPriority.select(0);
		}
	}
	
	public void save()
	{
		AccessTasks tasks = new AccessTasks();
		String title = txtTitle.getText();
		User creator = null;
		User assignedTo = null;
		
		for (User u : users.getUsers())
		{
			if (u.getUserName().equals(lblCreatedByField.getText()))
			{
				creator = u;
			}
			if (u.getUserName().equals(cboxAssignedTo.getText()))
			{
				assignedTo = u;
			}
		}
		Task task;
		try
		{
			task = new Task(title, creator, assignedTo);
			task.setComments(txtComments.getText());
			task.setDescription(txtDescription.getText());
			task.setUpdatedDate(Calendar.getInstance());
			task.setPriority(PriorityCode.valueOf(cboxPriority.getText()));
			task.setStatus(StatusCode.valueOf(cboxStatus.getText()));
			
			final int MIN_YEAR = 1800;
			Calendar currentDueDate = FormatDate
					.convertDateTimeToCalendar(dueDate);
			
			if (currentDueDate.get(Calendar.YEAR) < MIN_YEAR)
			{
				throw new IllegalArgumentException("Please enter a date with a year no less than " + MIN_YEAR);
			}
			
			task.setDueDate(currentDueDate);
			
			try
			{
				double number;
				number = Double.parseDouble(txtTimeEstimate.getText());
				task.setTimeEstimate(number);
				number = Double.parseDouble(txtTimeSpent.getText());
				task.setTimeSpent(number);
			}
			catch (NumberFormatException nfe)
			{
				MyMessageBox error = new MyMessageBox(shell, "Error", "Please enter a non-negative numeric value for Time Estimate and Time Spent.", "OK");
				error.open();
			}
			
			tasks.getTasks();
			tasks.addTask(task);

			MyMessageBox success = new MyMessageBox(shell, "Save succeeded", "Task has been saved", "OK");
			success.open();
			shell.close();
		}
		catch (IllegalArgumentException iae)
		{
			MyMessageBox error;
			if (iae.getMessage() == null)
			{
				error = new MyMessageBox(shell, "Illegal Field", "Check your inputs", "OK");
			}
			else
			{
				error = new MyMessageBox(shell, "Illegal Field", iae.getMessage(), "OK");
			}
			error.open();
		}
	}
}
