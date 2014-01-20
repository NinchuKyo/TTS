package tests.persistence;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import tts.application.Main;
import tts.objects.*;
import tts.persistence.DataAccess;

public class DataAccessStub implements DataAccess
{
	private String dbName;
	private String dbType = "stub";
	
	private List<User> users;
	private List<Task> tasks;
	
	public DataAccessStub(String dbName)
	{
		this.dbName = dbName;
		users = new ArrayList<User>();
		tasks = new ArrayList<Task>();
	}
	
	public DataAccessStub()
	{
		this(Main.dbName);
		users = new ArrayList<User>();
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public void open()
	{
		User user;
		Task task;
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		Calendar createdDate = Calendar.getInstance();
		
		cal1.set(2014, 8, 22);
		cal2.set(2015, 5, 22);
		cal3.set(2013, 9, 12);
		cal4.set(2013, 11, 1);
		createdDate.set(2013, 5, 1);
		
		user = new User("StudentInCOMP3350");
		users.add(user);
		
		user = new User("JamesBond");
		users.add(user);
		
		task = new Task("Complete iteration 1 for COMP 3350", users.get(0),
				users.get(0));
		task.setDueDate(cal1);
		task.setCreatedDate(createdDate);
		task.setComments("I don't know what to put for comments.");
		task.setPriority(PriorityCode.HIGH);
		task.setStatus(StatusCode.IN_PROGRESS);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		task = new Task("Complete the database design for iteration 1",
				users.get(0), users.get(0));
		
		task.setDueDate(cal2);
		task.setCreatedDate(createdDate);
		task.setComments("I don't know what to still put for comments.");
		task.setPriority(PriorityCode.MEDIUM);
		task.setStatus(StatusCode.IN_PROGRESS);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		task = new Task("Rescue the president", users.get(1), users.get(1));
		
		task.setDueDate(cal3);
		task.setCreatedDate(createdDate);
		task.setComments("Pretty important.");
		task.setPriority(PriorityCode.LOW);
		task.setStatus(StatusCode.ON_HOLD);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		task = new Task("Write the unit tests using JUnit", users.get(0),
				users.get(0));
		task.setDueDate(cal4);
		task.setCreatedDate(cal2);
		task.setComments("Moo.");
		task.setPriority(PriorityCode.UNDETERMINED);
		task.setStatus(StatusCode.COMPLETED);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		System.out.println("Opened " + dbType + " database " + dbName);
	}
	
	@Override
	public void close()
	{
		System.out.println("Closed " + dbType + " database " + dbName);
	}
	
	@Override
	public List<User> getUsersSequential()
	{
		return new ArrayList<User>(users);
	}
	
	@Override
	public boolean insertUser(User newUser)
	{
		boolean inserted = false;
		
		if (newUser != null && users.indexOf(newUser) < 0)
		{
			users.add(newUser);
			inserted = true;
		}
		
		return inserted;
	}
	
	@Override
	public List<Task> getTasksSequential()
	{
		return new ArrayList<Task>(tasks);
	}
	
	@Override
	public boolean insertTask(Task newTask)
	{
		boolean inserted = false;
		
		if (newTask != null && tasks.indexOf(newTask) < 0)
		{
			tasks.add(newTask);
			inserted = true;
		}
		
		return inserted;
	}
	
	@Override
	public boolean updateTask(Task currentTask)
	{
		boolean updated = false;
		
		if (currentTask != null)
		{
			int index = tasks.indexOf(currentTask);
			if (index >= 0)
			{
				tasks.set(index, currentTask);
				updated = true;
			}
		}
		
		return updated;
	}
	
	@Override
	public boolean deleteTask(Task toDelete)
	{
		boolean deleted = false;
		
		if (toDelete != null)
		{
			deleted = tasks.remove(toDelete);
		}
		
		return deleted;
	}

	@Override
	public void select(String source)
	{
		
	}

	@Override
	public String lookup(String key)
	{

		return null;
	}
	
}
