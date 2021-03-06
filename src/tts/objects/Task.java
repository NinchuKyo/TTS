package tts.objects;

import java.util.Calendar;

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
	private Calendar createdDate;
	private Calendar dueDate;
	private Calendar updatedDate;
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
		setCreatedDate(Calendar.getInstance());
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
		if (title == null || title.length() == 0
				|| title.length() > MAX_TITLE_LENGTH)
		{
			throw new IllegalArgumentException(
					"Title cannot be empty, or null, or exceed max length!");
		}
		this.title = title;
	}
	
	public int getTaskID()
	{
		return taskID;
	}
	
	public static int getNextTaskID()
	{
		return nextTaskID;
	}
	
	public void setTaskID(int taskID)
	{
		if (taskID < 1)
		{
			throw new IllegalArgumentException("Task ID cannot be less than 1!");
		}
		this.taskID = taskID;
	}
	
	public static void setNextTaskID(int staticTaskID)
	{
		if (staticTaskID < 1)
		{
			throw new IllegalArgumentException(
					"Next Task ID cannot be less than 1!");
		}
		nextTaskID = staticTaskID;
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
	
	public String getPriorityString()
	{
		return priority.toString();
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
	
	public String getStatusString()
	{
		return status.toString();
	}
	
	public void setStatus(StatusCode status)
	{
		if (status == null)
		{
			throw new IllegalArgumentException("Status cannot be null!");
		}
		this.status = status;
	}
	
	public Calendar getCreatedDate()
	{
		return createdDate;
	}
	
	public void setCreatedDate(Calendar createdDate)
	{
		if (createdDate == null)
		{
			throw new IllegalArgumentException("Created date cannot be null!");
		}
		this.createdDate = createdDate;
	}
	
	public Calendar getDueDate()
	{
		return dueDate;
	}
	
	public void setDueDate(Calendar dueDate)
	{
		this.dueDate = dueDate;
	}
	
	public Calendar getUpdatedDate()
	{
		return updatedDate;
	}
	
	public void setUpdatedDate(Calendar updatedDate)
	{
		this.updatedDate = updatedDate;
	}
}
