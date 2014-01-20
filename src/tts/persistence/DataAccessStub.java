package tts.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tts.application.Main;
import tts.objects.*;

public class DataAccessStub
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
	
	public void open(String dbName)
	{
		User user;
		Task task;
		
		user = new User("StudentInCOMP3350");
		users.add(user);
		
		user = new User("JamesBond");
		users.add(user);
		
		task = new Task("Complete iteration 1 for COMP 3350", users.get(0),
				users.get(0));
		task.setDueDate(new Date());
		task.setComments("I don't know what to put for comments.");
		task.setPriority(PriorityCode.HIGH);
		task.setStatus(StatusCode.IN_PROGRESS);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		task = new Task("Complete the database design for iteration 1",
				users.get(0), users.get(0));
		task.setDueDate(new Date());
		task.setComments("I don't know what to still put for comments.");
		task.setPriority(PriorityCode.MEDIUM);
		task.setStatus(StatusCode.IN_PROGRESS);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		task = new Task("Rescue the president", users.get(1), users.get(1));
		task.setDueDate(new Date());
		task.setComments("Pretty important.");
		task.setPriority(PriorityCode.LOW);
		task.setStatus(StatusCode.ON_HOLD);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		task = new Task("Write the unit tests using JUnit", users.get(0),
				users.get(0));
		task.setDueDate(new Date());
		task.setComments("Moo.");
		task.setPriority(PriorityCode.UNDETERMINED);
		task.setStatus(StatusCode.COMPLETED);
		task.setTimeSpent(2.0);
		tasks.add(task);
		
		System.out.println("Opened " + dbType + " database " + dbName);
	}
	
	public void close()
	{
		System.out.println("Closed " + dbType + " database " + dbName);
	}
	
	public List<User> getUsersSequential()
	{
		return users;
	}
	
	public void insertUser(User newUser)
	{
		if (newUser != null && users.indexOf(newUser) < 0)
		{
			users.add(newUser);
		}
	}
	
	public List<Task> getTasksSequential()
	{
		return tasks;
	}
	
	public void insertTask(Task newTask)
	{
		if (newTask != null && tasks.indexOf(newTask) < 0)
		{
			tasks.add(newTask);
		}
	}
	
	public void updateTask(Task currentTask)
	{
		if (currentTask != null)
		{
			int index = tasks.indexOf(currentTask);
			if (index >= 0)
			{
				tasks.set(index, currentTask);
			}
		}
	}
	
	public void deleteTask(Task currentTask)
	{
		if (currentTask != null)
		{
			int index = tasks.indexOf(currentTask);
			if (index >= 0)
			{
				tasks.remove(index);
			}
		}
	}
	
}
