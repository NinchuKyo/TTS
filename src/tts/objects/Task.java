package tts.objects;

import java.util.Date;

public class Task
{
	private String title;
	private int taskID;
	private static int nextTaskID = 1;
	private User creator;
	private User assignedTo;
	private String description;
	private String comments;
	private double timeEstimate;
	private double timeSpent;
	private PriorityCode priority;
	private StatusCode status;
	private Date createdDate;
	private Date dueDate;
	private Date updatedDate;
	public final static int MAX_TITLE_LENGTH = 64;
	public final static int MAX_TEXT_LENGTH = 512;
	
	public Task(String title, User creator, User assignedTo)
	{
		setTitle(title);
		setCreator(creator);
		setAssignedTo(assignedTo);
		setDescription("");
		setComments("");
		setTimeEstimate(0.0);
		setTimeSpent(0.0);
		setPriority(PriorityCode.UNDETERMINED);
		setStatus(StatusCode.CREATED);
		setCreatedDate(new Date());
		setDueDate(null);
		setUpdatedDate(null);
		setTaskID(nextTaskID);
		nextTaskID++;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		if (title == null || title.length() == 0 || hasIllegalCharacters(title)
				|| title.length() > MAX_TITLE_LENGTH)
		{
			throw new IllegalArgumentException(
					"Title cannot be empty, or null, or have illegal characters, or exceed max length!");
		}
		this.title = title;
	}
	
	public int getTaskID()
	{
		return taskID;
	}
	
	private void setTaskID(int taskID)
	{
		if (taskID < 1)
		{
			throw new IllegalArgumentException("Task ID cannot be less than 1!");
		}
		this.taskID = taskID;
	}
	
	public User getCreator()
	{
		return creator;
	}
	
	private void setCreator(User creator)
	{
		if (creator == null)
		{
			throw new IllegalArgumentException("Creator cannot be null!");
		}
		this.creator = creator;
	}
	
	public User getAssignedTo()
	{
		return assignedTo;
	}
	
	public void setAssignedTo(User assignedTo)
	{
		if (assignedTo == null)
		{
			throw new IllegalArgumentException(
					"\"Assigned to\" user cannot be null!");
		}
		this.assignedTo = assignedTo;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		if (description == null || description.length() > MAX_TEXT_LENGTH)
		{
			throw new IllegalArgumentException(
					"Description cannot be null, or exceed max length!");
		}
		this.description = description;
	}
	
	public String getComments()
	{
		return comments;
	}
	
	public void setComments(String comments)
	{
		if (comments == null || comments.length() > MAX_TEXT_LENGTH)
		{
			throw new IllegalArgumentException(
					"Comments cannot be null, or exceed max length!");
		}
		this.comments = comments;
	}
	
	public double getTimeEstimate()
	{
		return timeEstimate;
	}
	
	public void setTimeEstimate(double timeEstimate)
	{
		if (timeEstimate < 0)
		{
			throw new IllegalArgumentException(
					"Time estimate cannot be less than 0!");
		}
		this.timeEstimate = timeEstimate;
	}
	
	public double getTimeSpent()
	{
		return timeSpent;
	}
	
	public void setTimeSpent(double timeSpent)
	{
		if (timeSpent < 0)
		{
			throw new IllegalArgumentException(
					"Time spent cannot be less than 0!");
		}
		this.timeSpent = timeSpent;
	}
	
	public PriorityCode getPriority()
	{
		return priority;
	}
	
	public void setPriority(PriorityCode priority)
	{
		if (priority == null)
		{
			throw new IllegalArgumentException("Priority cannot be null!");
		}
		this.priority = priority;
	}
	
	public StatusCode getStatus()
	{
		return status;
	}
	
	public void setStatus(StatusCode status)
	{
		if (status == null)
		{
			throw new IllegalArgumentException("Status cannot be null!");
		}
		this.status = status;
	}
	
	public Date getCreatedDate()
	{
		return createdDate;
	}
	
	private void setCreatedDate(Date createdDate)
	{
		if (createdDate == null)
		{
			throw new IllegalArgumentException("Created date cannot be null!");
		}
		this.createdDate = createdDate;
	}
	
	public Date getDueDate()
	{
		return dueDate;
	}
	
	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}
	
	public Date getUpdatedDate()
	{
		return updatedDate;
	}
	
	public void setUpdatedDate(Date updatedDate)
	{
		this.updatedDate = updatedDate;
	}
	
	private boolean hasIllegalCharacters(String toCheck)
	{
		boolean hasIllegalChars = false;
		final String legalCharacters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890 ";
		
		for (int i = 0; i < toCheck.length(); i++)
		{
			if (legalCharacters.indexOf(toCheck.charAt(i)) < 0)
			{
				hasIllegalChars = true;
			}
		}
		
		return hasIllegalChars;
	}
}
