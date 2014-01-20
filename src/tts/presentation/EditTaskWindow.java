package tts.presentation;

import java.util.Calendar;
import java.util.List;

import org.eclipse.swt.widgets.TableItem;

import tts.objects.PriorityCode;
import tts.objects.StatusCode;
import tts.objects.User;
import tts.objects.Task;
import tts.business.AccessTasks;
import tts.business.FormatDate;

import acceptanceTests.Register;

public class EditTaskWindow extends TaskWindow
{	
	private Task theTask;
	private AccessTasks tasks;
	
	public EditTaskWindow()
	{
		super("Edit a task");
		Register.newWindow(this);
	}
	
	public EditTaskWindow(TableItem item)
	{
		super("Edit a task", item);
		Register.newWindow(this);
	}
	
	public void setTextFieldValues()
	{
		List<User> theUsers = users.getUsers();
		
		tasks = new AccessTasks();
		tasks.getTasks();
		theTask = null;
		
		if(tasks != null && item != null)
		{
			theTask = tasks.getTask(Integer.parseInt(item.getText()));
		}
		
		if(theTask != null)
		{
			StaticWindowMethods.populateAssignedToDropDown(cboxAssignedTo, theUsers);
			cboxAssignedTo.select(cboxAssignedTo.indexOf(theTask.getAssignedTo().getUserName()));
		
			if (StatusCode.values().length > 0)
			{
				StaticWindowMethods.populateStatusDropDown(cboxStatus);
				theTask.getStatus();
				cboxStatus.select(cboxStatus.indexOf(StatusCode.valueOf(theTask.getStatus().toString()).toString())); 
			}
			
			if (PriorityCode.values().length > 0)
			{
				StaticWindowMethods.populatePriorityDropDown(cboxPriority);
				cboxPriority.select(cboxPriority.indexOf(PriorityCode.valueOf(theTask.getPriority().toString()).toString())); 
			}
			
			lblCreatedByField.setText(theTask.getCreator().getUserName());
			lblCreatedDateField.setText(FormatDate.formatDate(theTask.getCreatedDate()));
			txtTitle.setText(theTask.getTitle());
			txtTimeEstimate.setText(""+theTask.getTimeEstimate());
			txtTimeSpent.setText(""+theTask.getTimeSpent());
			txtDescription.setText(theTask.getDescription());
			txtComments.setText(theTask.getComments());
			dueDate.setDay(theTask.getDueDate().get(Calendar.DAY_OF_MONTH));
			dueDate.setMonth(theTask.getDueDate().get(Calendar.MONTH));
			dueDate.setYear(theTask.getDueDate().get(Calendar.YEAR));
		}
	}

	public void save()
	{
		
		User assignedTo = null;
		
		for (User u : users.getUsers())
		{
			if (u.getUserName().equals(cboxAssignedTo.getText()))
			{
				assignedTo = u;
			}
		}
		try
		{
			theTask.setTitle(txtTitle.getText());
			theTask.setComments(txtComments.getText());
			theTask.setDescription(txtDescription.getText());
			theTask.setDueDate(FormatDate.convertDateTimeToCalendar(dueDate));
			theTask.setUpdatedDate(Calendar.getInstance());
			theTask.setPriority(PriorityCode.valueOf(cboxPriority.getText()));
			theTask.setStatus(StatusCode.valueOf(cboxStatus.getText()));
			theTask.setAssignedTo(assignedTo);
			try
			{
				double number;
				number = Double.parseDouble(txtTimeEstimate.getText());
				theTask.setTimeEstimate(number);
				number = Double.parseDouble(txtTimeSpent.getText());
				theTask.setTimeSpent(number);
			}
			catch (NumberFormatException nfe)
			{
				MyMessageBox error = new MyMessageBox(shell, "Error", "Please enter a non-negative numeric value for Time Estimate and Time Spent.", "OK");
				error.open();
			}
			
			tasks.getTasks();
			tasks.editTask(theTask);
			MyMessageBox success = new MyMessageBox(shell, "Save succeeded", "Task has been updated.", "OK");
			success.open();
			shell.close();
		}
		catch(IllegalArgumentException iae)
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
