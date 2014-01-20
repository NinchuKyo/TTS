package tests.business;

import java.util.Calendar;
import java.util.List;

import tests.persistence.DataAccessStub;
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
	private Calendar date1, date2, date3, date4, date5, tempDate;
	
	public TestAccessTasks(String arg0)
	{
		super(arg0);
		
		Services.closeDataAccess();
		System.out.println("\nStarting test AccessTasks");
		Services.createDataAccess(new DataAccessStub());
		
		tasksAccess = new AccessTasks();
		
		user1 = new User("Bob");
		user2 = new User("Bill");
		date1 = Calendar.getInstance();
		date2 = Calendar.getInstance();
		date3 = Calendar.getInstance();
		date4 = Calendar.getInstance();
		date5 = Calendar.getInstance();
		tempDate = Calendar.getInstance();
	}
	
	public void setUp()
	{
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
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			tasksAccess.editTask(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testNull: task functions");
	}
	
	public void testInvalidSortColumn()
	{
		System.out.println("\nStarting testInvalidSortColumn: task functions");
		
		assertNotNull(tasksAccess);
		
		try
		{
			tasksAccess.sort(-1, false);
			fail("Illegal Argument Exception expected!");
		}
		catch(IllegalArgumentException iae)
		{
			
		}
		
		try
		{
			tasksAccess.sort(7, false);
			fail("Illegal Argument Exception expected!");
		}
		catch(IllegalArgumentException iae)
		{
			
		}
				
		try
		{
			tasksAccess.sort(1000000000, false);
			fail("Illegal Argument Exception expected!");
		}
		catch(IllegalArgumentException iae)
		{
		}
				
		System.out.println("Finished testInvalidSortColumn: task functions");
	}
	
	public void testOneTask()
	{
		System.out.println("\nStarting testOneTask");
		
		tasks = tasksAccess.getTasks();
		
		assertNotNull(tasksAccess);
				
		date1.set(2013, 7, 7);
		task1.setDueDate(date1);
		
		tasksAccess.sort(0, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() >= tasks.get(i+1).getTaskID());
		}
		
		tasksAccess.sort(0, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() <= tasks.get(i+1).getTaskID());
		}
		
		tasksAccess.sort(1, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTitle().compareTo(tasks.get(i+1).getTitle()) <= 0);
		}
		
		tasksAccess.sort(4, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getDueDate().compareTo(tasks.get(i+1).getDueDate()) <= 0);
		}
		
		for(int i = tasks.size()-1; i>=0; i--)
		{
			tasksAccess.deleteTask(tasks.get(i).getTaskID());
		}
		
		assertEquals(tasks.size(), 0);
		
		tasksAccess.sort(4, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getDueDate().compareTo(tasks.get(i+1).getDueDate()) <= 0);
		}
		
		assertEquals(tasks.size(), 0);
		
		tasks = tasksAccess.getTasks();
		count = tasks.size();
		
		tasksAccess.addTask(task1);
		tasks = tasksAccess.getTasks();
		
		tempDate = tasksAccess.getMinDueDate();
		assertEquals(tempDate.get(Calendar.DAY_OF_MONTH), date1.get(Calendar.DAY_OF_MONTH));
		assertEquals(tempDate.get(Calendar.MONTH), date1.get(Calendar.MONTH));
		assertEquals(tempDate.get(Calendar.YEAR), date1.get(Calendar.YEAR));
		tempDate = tasksAccess.getMaxDueDate();
		assertEquals(tempDate.get(Calendar.DAY_OF_MONTH), date1.get(Calendar.DAY_OF_MONTH));
		assertEquals(tempDate.get(Calendar.MONTH), date1.get(Calendar.MONTH));
		assertEquals(tempDate.get(Calendar.YEAR), date1.get(Calendar.YEAR));
				
		tasksAccess.sort(4, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getDueDate().compareTo(tasks.get(i+1).getDueDate()) <= 0);
		}
		
		tasksAccess.sort(3, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getStatus().compareTo(tasks.get(i+1).getStatus()) <= 0);
		}
		
		tasksAccess.sort(0, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() <= tasks.get(i+1).getTaskID());
		}
		
		assertEquals(tasks.size(), count + 1);
		assertEquals("task1", tasks.get(tasks.size() - 1).getTitle());
		
		task1.setComments("edited");
		tasksAccess.editTask(task1);
		tasks = tasksAccess.getTasks();
		
		assertEquals(tasks.get(tasks.indexOf(task1)).getComments(), "edited");
		
		count = tasks.size();
		tasksAccess.deleteTask(task1.getTaskID());
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
		
		date1.set(2013, 0, 2);
		task1.setDueDate(date1);
		date2.set(2013, 0, 1);
		task2.setDueDate(date2);
		date3.set(2013, 5, 5);
		task3.setDueDate(date3);
		date4.set(2114, 11, 31);
		task4.setDueDate(date4);
		date5.set(2114, 11, 11);
		task5.setDueDate(date5);
		
		tasksAccess.addTask(task1);
		tasksAccess.addTask(task2);
		tasksAccess.addTask(task3);
		tasksAccess.addTask(task4);
		tasksAccess.addTask(task5);
		
		assertEquals(tasks.size(), count+5);
		
		tasksAccess.sort(0, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() >= tasks.get(i+1).getTaskID());
		}
		
		tasksAccess.sort(0, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() <= tasks.get(i+1).getTaskID());
		}
				
		tasksAccess.sort(2, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getAssignedTo().getUserName().compareTo(tasks.get(i+1).getAssignedTo().getUserName()) >= 0);
		}		
		
		tasksAccess.sort(4, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getDueDate().compareTo(tasks.get(i+1).getDueDate()) <= 0);
		}
		
		tasksAccess.sort(6, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getDescription().compareTo(tasks.get(i+1).getDescription()) <= 0);
		}
		
		tasksAccess.sort(0, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() <= tasks.get(i+1).getTaskID());
		}
		
		tempDate = tasksAccess.getMinDueDate();
		assertEquals(tempDate.get(Calendar.DAY_OF_MONTH), date2.get(Calendar.DAY_OF_MONTH));
		assertEquals(tempDate.get(Calendar.MONTH), date2.get(Calendar.MONTH));
		assertEquals(tempDate.get(Calendar.YEAR), date2.get(Calendar.YEAR));
		tempDate = tasksAccess.getMaxDueDate();
		assertEquals(tempDate.get(Calendar.DAY_OF_MONTH), date4.get(Calendar.DAY_OF_MONTH));
		assertEquals(tempDate.get(Calendar.MONTH), date4.get(Calendar.MONTH));
		assertEquals(tempDate.get(Calendar.YEAR), date4.get(Calendar.YEAR));
		
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.size() == count + 5);
		assertEquals("task5", tasks.get(tasks.size() - 1).getTitle());
		assertEquals("task4", tasks.get(tasks.size() - 2).getTitle());
		assertEquals("task3", tasks.get(tasks.size() - 3).getTitle());
		assertEquals("task2", tasks.get(tasks.size() - 4).getTitle());
		assertEquals("task1", tasks.get(tasks.size() - 5).getTitle());
		
		task1.setDescription("newDescription");
		task2.setDueDate(date5);
		task3.setPriority(PriorityCode.MEDIUM);
		task4.setStatus(StatusCode.IN_PROGRESS);
		task5.setTimeEstimate(999.999);
		
		tasksAccess.editTask(task1);
		tasksAccess.editTask(task2);
		tasksAccess.editTask(task3);
		tasksAccess.editTask(task4);
		tasksAccess.editTask(task5);
		
		tasksAccess.sort(0, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() <= tasks.get(i+1).getTaskID());
		}
		
		tasksAccess.sort(0, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getTaskID() >= tasks.get(i+1).getTaskID());
		}
		
		tasksAccess.sort(2, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getAssignedTo().getUserName().compareTo(tasks.get(i+1).getAssignedTo().getUserName()) <= 0);
		}
		
		tasksAccess.sort(3, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getStatus().toString().compareTo(tasks.get(i+1).getStatus().toString()) >= 0);
		}
		
		tasksAccess.sort(4, true);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getDueDate().compareTo(tasks.get(i+1).getDueDate()) <= 0);
		}
		
		tasksAccess.sort(5, false);
		for(int i = 0; i < tasks.size()-1; i++)
		{
			assertTrue(tasks.get(i).getPriority().toString().compareTo(tasks.get(i+1).getPriority().toString()) >= 0);
		}
		tasksAccess.sort(0, false);
		
		tasks = tasksAccess.getTasks();
		
		assertEquals(tasks.get(tasks.indexOf(task1)).getDescription(),
				"newDescription");
		assertEquals(tasks.get(tasks.indexOf(task2)).getDueDate(), date5);
		assertEquals(tasks.get(tasks.indexOf(task3)).getPriority(),
				PriorityCode.MEDIUM);
		assertEquals(tasks.get(tasks.indexOf(task4)).getStatus(),
				StatusCode.IN_PROGRESS);
		assertEquals(tasks.get(tasks.indexOf(task5)).getTimeEstimate(), 999.999);
		assertEquals(task1, tasks.get(tasks.size() - 5));
		
		try
		{
			tasksAccess.getTask(0);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		count = tasks.size();
		tasksAccess.deleteTask(task1.getTaskID());
		assertTrue(tasks.indexOf(task1) < 0);
		assertTrue(tasks.indexOf(task2) >= 0);
		assertTrue(tasks.indexOf(task3) >= 0);
		assertTrue(tasks.indexOf(task4) >= 0);
		assertTrue(tasks.indexOf(task5) >= 0);
		tasksAccess.deleteTask(task2.getTaskID());
		assertTrue(tasks.indexOf(task2) < 0);
		assertTrue(tasks.indexOf(task3) >= 0);
		assertTrue(tasks.indexOf(task4) >= 0);
		assertTrue(tasks.indexOf(task5) >= 0);
		tasksAccess.deleteTask(task3.getTaskID());
		assertTrue(tasks.indexOf(task3) < 0);
		assertTrue(tasks.indexOf(task4) >= 0);
		assertTrue(tasks.indexOf(task5) >= 0);
		tasksAccess.deleteTask(task4.getTaskID());
		assertTrue(tasks.indexOf(task4) < 0);
		assertTrue(tasks.indexOf(task5) >= 0);
		tasksAccess.deleteTask(task5.getTaskID());
		assertTrue(tasks.indexOf(task5) < 0);
		
		tasks = tasksAccess.getTasks();
		
		assertTrue(tasks.size() == count - 5);		
		
		System.out.println("Finished testMultipleTasks");
	}
	
	public void testFilerByNothing()
	{
		System.out.println("\nStarting testFilterByNothing");
		
		List<Task> filteredTasks;
		Calendar low = Calendar.getInstance();
		low.set(Calendar.YEAR, 1000);
		low.set(Calendar.MONTH, 1);
		low.set(Calendar.DAY_OF_MONTH, 1);
		task1.setDueDate(Calendar.getInstance());
		task2.setDueDate(Calendar.getInstance());
		task3.setDueDate(Calendar.getInstance());
		task4.setDueDate(Calendar.getInstance());
		task5.setDueDate(Calendar.getInstance());
		Calendar high = Calendar.getInstance();
		
		tasks = tasksAccess.getTasks();
		tasksAccess.addTask(task1);
		tasksAccess.addTask(task2);
		tasksAccess.addTask(task3);
		tasksAccess.addTask(task4);
		tasksAccess.addTask(task5);
		filteredTasks = tasksAccess.getFilteredTasks("", AccessTasks.NO_FILTER, AccessTasks.NO_FILTER, low, high, AccessTasks.NO_FILTER);
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertTrue(filteredTasks.contains(task1));
		assertTrue(filteredTasks.contains(task2));
		assertTrue(filteredTasks.contains(task3));
		assertTrue(filteredTasks.contains(task4));
		assertTrue(filteredTasks.contains(task5));
		
		System.out.println("Finished testFilterByNothing");
	}
	
	public void testFilterByOneOption()
	{
		System.out.println("\nStarting testFilterByOneOption");
		
		List<Task> filteredTasks;
		Calendar low = Calendar.getInstance();
		low.set(Calendar.YEAR, 1000);
		low.set(Calendar.MONTH, 1);
		low.set(Calendar.DAY_OF_MONTH, 1);
		task1.setDueDate(Calendar.getInstance());
		task2.setDueDate(Calendar.getInstance());
		task2.setStatus(StatusCode.CANCELLED);
		task3.setDueDate(Calendar.getInstance());
		task3.setStatus(StatusCode.COMPLETED);
		task3.setAssignedTo(user1);
		task3.setPriority(PriorityCode.LOW);
		task4.setDueDate(Calendar.getInstance());
		task4.setStatus(StatusCode.IN_PROGRESS);
		task4.setPriority(PriorityCode.MEDIUM);
		task5.setDueDate(Calendar.getInstance());
		task5.setStatus(StatusCode.ON_HOLD);
		task5.setPriority(PriorityCode.HIGH);
		Calendar high = Calendar.getInstance();
		
		tasks = tasksAccess.getTasks();
		tasksAccess.addTask(task1);
		tasksAccess.addTask(task2);
		tasksAccess.addTask(task3);
		tasksAccess.addTask(task4);
		tasksAccess.addTask(task5);
		filteredTasks = tasksAccess.getFilteredTasks("task", AccessTasks.NO_FILTER, AccessTasks.NO_FILTER, low, high, AccessTasks.NO_FILTER);
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertTrue(filteredTasks.contains(task1));
		assertTrue(filteredTasks.contains(task2));
		assertTrue(filteredTasks.contains(task3));
		assertTrue(filteredTasks.contains(task4));
		assertTrue(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("1", AccessTasks.NO_FILTER, AccessTasks.NO_FILTER, low, high, AccessTasks.NO_FILTER);
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertTrue(filteredTasks.contains(task1));
		assertFalse(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("", AccessTasks.NO_FILTER, StatusCode.CANCELLED.toString(), low, high, AccessTasks.NO_FILTER);
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertTrue(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("", "Bob", AccessTasks.NO_FILTER, low, high, AccessTasks.NO_FILTER);
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertFalse(filteredTasks.contains(task2));
		assertTrue(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("", AccessTasks.NO_FILTER, AccessTasks.NO_FILTER, low, high, PriorityCode.HIGH.toString());
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertFalse(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertTrue(filteredTasks.contains(task5));
		
		System.out.println("Finished testFilterByOneOption");
	}
	
	public void testFilterByMultipleOptions()
	{
		System.out.println("\nStarting testFilterByMultipleOptions");
		
		List<Task> filteredTasks;
		Calendar low = Calendar.getInstance();
		low.set(Calendar.YEAR, 1000);
		low.set(Calendar.MONTH, 1);
		low.set(Calendar.DAY_OF_MONTH, 1);
		task1.setDueDate(Calendar.getInstance());
		task2.setDueDate(Calendar.getInstance());
		task2.setStatus(StatusCode.CANCELLED);
		task3.setDueDate(Calendar.getInstance());
		task3.setStatus(StatusCode.COMPLETED);
		task3.setAssignedTo(user1);
		task3.setPriority(PriorityCode.LOW);
		task4.setDueDate(Calendar.getInstance());
		task4.setStatus(StatusCode.IN_PROGRESS);
		task4.setPriority(PriorityCode.MEDIUM);
		task5.setDueDate(Calendar.getInstance());
		task5.setStatus(StatusCode.ON_HOLD);
		task5.setPriority(PriorityCode.HIGH);
		Calendar high = Calendar.getInstance();
		
		tasks = tasksAccess.getTasks();
		tasksAccess.addTask(task1);
		tasksAccess.addTask(task2);
		tasksAccess.addTask(task3);
		tasksAccess.addTask(task4);
		tasksAccess.addTask(task5);
		filteredTasks = tasksAccess.getFilteredTasks("task", "Bill", StatusCode.IN_PROGRESS.toString(), low, high, PriorityCode.MEDIUM.toString());
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertFalse(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertTrue(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("task", "Bob", StatusCode.IN_PROGRESS.toString(), low, high, PriorityCode.MEDIUM.toString());
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertFalse(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("", "Bill", StatusCode.CANCELLED.toString(), low, high, PriorityCode.MEDIUM.toString());
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertFalse(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		filteredTasks = tasksAccess.getFilteredTasks("", "Bill", StatusCode.CANCELLED.toString(), low, high, AccessTasks.NO_FILTER);
		assertTrue(tasks.contains(task1));
		assertTrue(tasks.contains(task2));
		assertTrue(tasks.contains(task3));
		assertTrue(tasks.contains(task4));
		assertTrue(tasks.contains(task5));
		assertFalse(filteredTasks.contains(task1));
		assertTrue(filteredTasks.contains(task2));
		assertFalse(filteredTasks.contains(task3));
		assertFalse(filteredTasks.contains(task4));
		assertFalse(filteredTasks.contains(task5));
		
		System.out.println("Finished testFilterByMultipleOptions");
	}
}
