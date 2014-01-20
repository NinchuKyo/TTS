package tests.objects;

import java.util.Date;
import junit.framework.TestCase;
import tts.objects.*;

public class TestTask extends TestCase
{
	private Task newTask;
	private User john;
	private User jane;
	private String title;
	private String illegalTitle;
	private String longTitle;
	private String tooLongTitle;
	private String shortTitle;
	private String comment;
	private String description;
	private String longText;
	private Date now;
	
	public TestTask(String arg0)
	{
		super(arg0);
	}
	
	public void setUp()
	{
		john = new User("John");
		jane = new User("Jane");
		shortTitle = "a";
		title = "1234567890 This is a legal title 1234567890";
		longTitle = "This Title is 64 chars long aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		tooLongTitle = "This Title is longer than 64 chars aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		description = "This is a description.";
		comment = "This is a comment.";
		longText = "qwertyuiopasdfghjklzxcvbnmmmmmmm1234567890-=[];'\\/.,`~!@#$%^&*()_+}{:\"|?><QWQWERTYUIOPASDFGHJKLLLLLZXCVBNM`1234567890-=QWERTYUIOP[]ASDFGHJKL;'\\ZXCVBNM,./1234567890-=qwertyuiop[]asdfghjkl;'\\zxcvbnm,./<>"
				+ "\n\n\t\tqwertyuiopasdfghjklzxcvbnmmmmmmm1234567890-=[];\'\\/.,`~!@#$%^&*()_+}{:\"|?><QWQWERTYUIOPASDFGHJKLLLLLZXCVBNM`1234567890-=QWERTYUIOP[]ASDFGHJKL;\'\\ZXCVBNM,./1234567890-=qwertyuiop[]asdfghjkl;\'\\zxcvbnm,./<>	"
				+ "ASDFGBHNJMKL.;,lkomjinuhgvtfcdrx";
		now = new Date();
	}
	
	public void testNull()
	{
		System.out.println("\nStarting testNull: tasks");
		
		try
		{
			new Task(null, john, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			new Task(title, null, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			new Task(title, john, null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			new Task(title, null, null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			new Task(null, null, null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		newTask = new Task(title, john, jane);
		
		try
		{
			newTask.setDescription(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			newTask.setComments(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			newTask.setPriority(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			newTask.setStatus(null);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testNull: tasks");
	}
	
	public void testEmptyString()
	{
		System.out.println("\nStarting testEmptyString: tasks");
		
		try
		{
			new Task("", john, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testEmptyString: tasks");
	}
	
	public void testIllegalInput()
	{
		System.out.println("\nStarting testIllegalInput: tasks");
		
		illegalTitle = "IllegalTitle$*%";
		
		try
		{
			new Task(illegalTitle, john, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		illegalTitle = ",.;:'\" IllegalTitle";
		
		try
		{
			new Task(illegalTitle, john, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		illegalTitle = "Illegal=+[}Title";
		
		try
		{
			new Task(illegalTitle, john, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		newTask = new Task(title, john, jane);
		
		try
		{
			newTask.setTimeEstimate(-1.0);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			newTask.setTimeSpent(-1.0);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testIllegalInput: tasks");
	}
	
	public void testTooLongStrings()
	{
		System.out.println("\nStarting testTooLongStrings: tasks");
		
		try
		{
			new Task(tooLongTitle, john, jane);
			fail("Illegal Argument Exception expected!");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testTooLongStrings: tasks");
	}
	
	public void testOneTask()
	{
		System.out.println("\nStarting testOneTask: tasks");
		
		newTask = new Task(title, john, jane);
		
		assertEquals(newTask.getTitle(), title);
		assertEquals(newTask.getCreator().getUserName(), "John");
		assertEquals(newTask.getAssignedTo().getUserName(), "Jane");
		assertEquals(newTask.getDescription(), "");
		assertEquals(newTask.getComments(), "");
		assertEquals(newTask.getTimeEstimate(), 0.0);
		assertEquals(newTask.getTimeSpent(), 0.0);
		assertEquals(newTask.getPriority(), PriorityCode.UNDETERMINED);
		assertEquals(newTask.getStatus(), StatusCode.CREATED);
		assertTrue(newTask.getCreatedDate() != null);
		assertEquals(newTask.getDueDate(), null);
		assertEquals(newTask.getUpdatedDate(), null);
		assertTrue(newTask.getTaskID() > 0);
		
		newTask.setTitle(longTitle);
		assertEquals(newTask.getTitle(), longTitle);
		newTask.setAssignedTo(john);
		assertEquals(newTask.getAssignedTo().getUserName(), "John");
		newTask.setDescription(description);
		assertEquals(newTask.getDescription(), description);
		newTask.setComments(comment);
		assertEquals(newTask.getComments(), comment);
		newTask.setTimeEstimate(12.3);
		assertEquals(newTask.getTimeEstimate(), 12.3);
		newTask.setTimeSpent(45.6);
		assertEquals(newTask.getTimeSpent(), 45.6);
		newTask.setPriority(PriorityCode.HIGH);
		assertEquals(newTask.getPriority(), PriorityCode.HIGH);
		newTask.setStatus(StatusCode.COMPLETED);
		assertEquals(newTask.getStatus(), StatusCode.COMPLETED);
		newTask.setDueDate(now);
		assertEquals(newTask.getDueDate(), now);
		newTask.setUpdatedDate(now);
		assertEquals(newTask.getUpdatedDate(), now);
		
		System.out.println("Finished testOneTask: tasks");
	}
	
	public void testBorderCases()
	{
		System.out.println("\nStarting testBorderCases: tasks");
		
		newTask = new Task(shortTitle, john, jane);
		now = new Date();
		
		newTask.setTitle(longTitle);
		assertEquals(newTask.getTitle(), longTitle);
		newTask.setAssignedTo(john);
		assertEquals(newTask.getAssignedTo().getUserName(), "John");
		newTask.setDescription(longText);
		assertEquals(newTask.getDescription(), longText);
		newTask.setComments(longText);
		assertEquals(newTask.getComments(), longText);
		newTask.setTimeEstimate(1234123412.213412343);
		assertEquals(newTask.getTimeEstimate(), 1234123412.213412343);
		newTask.setTimeSpent(9876543213456.9876543567);
		assertEquals(newTask.getTimeSpent(), 9876543213456.9876543567);
		newTask.setPriority(PriorityCode.MEDIUM);
		assertEquals(newTask.getPriority(), PriorityCode.MEDIUM);
		newTask.setStatus(StatusCode.CANCELLED);
		assertEquals(newTask.getStatus(), StatusCode.CANCELLED);
		newTask.setDueDate(now);
		assertEquals(newTask.getDueDate(), now);
		newTask.setUpdatedDate(now);
		assertEquals(newTask.getUpdatedDate(), now);
		
		System.out.println("Finished testBorderCases: tasks");
	}
	
}
