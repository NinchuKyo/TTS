package tests.business;

import java.util.List;
import java.util.Date;

import tts.application.Main;
import tts.application.Services;
import tts.business.AccessTasks;
import tts.objects.PriorityCode;
import tts.objects.StatusCode;
import tts.objects.Task;
import tts.objects.User;
import junit.framework.TestCase;

public class TestAccessTasks extends TestCase
{
	private AccessTasks tasksAccess;
	private List<Task> tasks;
	private Task task1, task2, task3, task4, task5;
	private int count;
	private User user1, user2;
	
	public TestAccessTasks(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		Services.createDataAccess(Main.dbName);
		tasksAccess = new AccessTasks();
		
		user1 = new User("Bob");
		user2 = new User("Bill");
		
		task1 = new Task("task1", user1, user2);
		task2 = new Task("task2", user1, user2);
		task3 = new Task("task3", user1, user2);
		task4 = new Task("task4", user1, user2);
		task5 = new Task("task5", user1, user2);
	}
	
	public void testNull()
	{
		System.out.println("\nStarting testNull: task functions");
		
		assertNotNull(tasksAccess);
		
		try
		{
			tasksAccess.addTask(null);
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			tasksAccess.editTask(null);
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			tasksAccess.deleteTask(null);
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testNull: task functions");
	}
	
	public void testOneTask()
	{
		System.out.println("\nStarting testOneTask");
		
		assertNotNull(tasksAccess);
		
		tasks = tasksAccess.getTasks();
		count = tasks.size();
		
		tasksAccess.addTask(task1);
		tasks = tasksAccess.getTasks();
		
		assertEquals(tasks.size(), count + 1);
		assertEquals(task1.getTaskID(), tasks.get(tasks.size() - 1).getTaskID());
		
		task1.setComments("edited");
		tasksAccess.editTask(task1);
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.get(tasks.indexOf(task1)).getComments()
				.equals("edited"));
		
		count = tasks.size();
		tasksAccess.deleteTask(task1);
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.size() == count - 1);
		assertTrue(tasks.indexOf(task1) < 0);
		
		System.out.println("Finished testOneTask");
	}
	
	public void testMultipleTasks()
	{
		System.out.println("\nStarting testMultipleTasks");
		
		assertNotNull(tasksAccess);
		
		tasks = tasksAccess.getTasks();
		count = tasks.size();
		
		tasksAccess.addTask(task1);
		tasksAccess.addTask(task2);
		tasksAccess.addTask(task3);
		tasksAccess.addTask(task4);
		tasksAccess.addTask(task5);
		
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.size() == count + 5);
		assertEquals(task5.getTaskID(), tasks.get(tasks.size() - 1).getTaskID());
		assertEquals(task4.getTaskID(), tasks.get(tasks.size() - 2).getTaskID());
		assertEquals(task3.getTaskID(), tasks.get(tasks.size() - 3).getTaskID());
		assertEquals(task2.getTaskID(), tasks.get(tasks.size() - 4).getTaskID());
		assertEquals(task1.getTaskID(), tasks.get(tasks.size() - 5).getTaskID());
		
		Date now = new Date();
		task1.setDescription("newDescription");
		task2.setDueDate(now);
		task3.setPriority(PriorityCode.MEDIUM);
		task4.setStatus(StatusCode.IN_PROGRESS);
		task5.setTimeEstimate(999.999);
		
		tasksAccess.editTask(task1);
		tasksAccess.editTask(task2);
		tasksAccess.editTask(task3);
		tasksAccess.editTask(task4);
		tasksAccess.editTask(task5);
		
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.get(tasks.indexOf(task1)).getDescription()
				.equals("newDescription"));
		assertEquals(tasks.get(tasks.indexOf(task2)).getDueDate(), now);
		assertEquals(tasks.get(tasks.indexOf(task3)).getPriority(),
				PriorityCode.MEDIUM);
		assertEquals(tasks.get(tasks.indexOf(task4)).getStatus(),
				StatusCode.IN_PROGRESS);
		assertEquals(tasks.get(tasks.indexOf(task5)).getTimeEstimate(), 999.999);
		
		count = tasks.size();
		tasksAccess.deleteTask(task1);
		tasksAccess.deleteTask(task5);
		tasksAccess.deleteTask(task3);
		tasksAccess.deleteTask(task2);
		tasksAccess.deleteTask(task4);
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.size() == count - 5);
		assertTrue(tasks.indexOf(task1) < 0);
		assertTrue(tasks.indexOf(task2) < 0);
		assertTrue(tasks.indexOf(task3) < 0);
		assertTrue(tasks.indexOf(task4) < 0);
		assertTrue(tasks.indexOf(task5) < 0);
		
		System.out.println("Finished testMultipleTasks");
	}
}
