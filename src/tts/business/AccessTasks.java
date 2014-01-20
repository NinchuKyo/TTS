package tts.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import tts.application.Services;
import tts.objects.Task;
import tts.persistence.DataAccess;

public class AccessTasks
{
	private DataAccess dataAccess;
	private List<Task> tasks;
	public final static String NO_FILTER = "Do not filter";
	
	public AccessTasks()
	{
		dataAccess = (DataAccess) Services.getDataAccess();
		tasks = null;
	}
	
	public List<Task> getTasks()
	{
		if (tasks == null)
		{
			tasks = dataAccess.getTasksSequential();
		}
		
		return tasks;
	}
	
	public boolean editTask(Task toUpdate)
	{
		boolean edited = false;
		
		if (toUpdate != null)
		{
			int index = tasks.indexOf(toUpdate);
			if (index >= 0)
			{
				tasks.set(index, toUpdate);
			}
			
			edited = dataAccess.updateTask(toUpdate);
		}
		else
		{
			throw new IllegalArgumentException("Cannot edit null!");
		}
		
		return edited;
	}
	
	public boolean addTask(Task toAdd)
	{
		boolean inserted = false;
		
		if (toAdd != null)
		{
			if (tasks.indexOf(toAdd) < 0)
			{
				tasks.add(toAdd);
				inserted = dataAccess.insertTask(toAdd);
			}
		}
		else
		{
			throw new IllegalArgumentException("Cannot add null!");
		}
		
		return inserted;
	}
	
	public boolean deleteTask(int taskID)
	{
		Task toDelete = getTask(taskID);
		
		boolean deleted = false;
		
		if (toDelete != null)
		{
			tasks.remove(toDelete);
			deleted = dataAccess.deleteTask(toDelete);
		}
		
		return deleted;
	}
	
	public Task getTask(int taskID)
	{
		
		Task task = null;
		
		if (taskID >= 1)
		{
			for (Task t : tasks)
			{
				if (t.getTaskID() == taskID)
				{
					task = t;
					break;
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("illegal task id");
		}
		
		return task;
	}
	
	public Calendar getMinDueDate()
	{
		Calendar result = Calendar.getInstance();
		Calendar min = null;
		Calendar toCheck;
		if (tasks != null && tasks.size() > 0)
		{
			for (Task t : tasks)
			{
				toCheck = t.getDueDate();
				if (min == null || toCheck.compareTo(min) < 0)
				{
					min = toCheck;
				}
			}
			result.set(Calendar.YEAR, min.get(Calendar.YEAR));
			result.set(Calendar.MONTH, min.get(Calendar.MONTH));
			result.set(Calendar.DAY_OF_MONTH, min.get(Calendar.DAY_OF_MONTH));
		}
		return result;
	}
	
	public Calendar getMaxDueDate()
	{
		Calendar result = Calendar.getInstance();
		Calendar max = null;
		Calendar toCheck;
		if (tasks != null && tasks.size() > 0)
		{
			for (Task t : tasks)
			{
				toCheck = t.getDueDate();
				if (max == null || toCheck.compareTo(max) > 0)
				{
					max = toCheck;
				}
			}
			result.set(Calendar.YEAR, max.get(Calendar.YEAR));
			result.set(Calendar.MONTH, max.get(Calendar.MONTH));
			result.set(Calendar.DAY_OF_MONTH, max.get(Calendar.DAY_OF_MONTH));
		}
		return result;
	}
	
	public List<Task> getFilteredTasks(String title, String assignedTo,
			String status, Calendar fromDate, Calendar toDate, String priority)
	{
		List<Task> filteredTasks = new ArrayList<Task>();
		boolean containsTitle;
		boolean isUser;
		boolean isStatus;
		boolean afterFromDate;
		boolean beforeToDate;
		boolean isPriority;
		
		for (Task t : tasks)
		{
			containsTitle = title.equals("")
					|| t.getTitle().toLowerCase().contains(title.toLowerCase());
			isUser = assignedTo.equals(NO_FILTER)
					|| t.getAssignedTo().equals(assignedTo);
			isStatus = status.equals(NO_FILTER)
					|| t.getStatusString().equals(status);
			afterFromDate = compareDates(fromDate, t.getDueDate()) <= 0;
			beforeToDate = compareDates(toDate, t.getDueDate()) >= 0;
			isPriority = priority.equals(NO_FILTER)
					|| t.getPriorityString().equals(priority);
			
			if (containsTitle && isUser && isStatus && afterFromDate
					&& beforeToDate && isPriority)
			{
				filteredTasks.add(t);
			}
		}
		
		return filteredTasks;
	}
	
	private int compareDates(Calendar base, Calendar toCompare)
	{
		int result;
		
		if (toCompare.get(Calendar.YEAR) > base.get(Calendar.YEAR)
				|| (toCompare.get(Calendar.YEAR) == base.get(Calendar.YEAR) && toCompare
						.get(Calendar.MONTH) > base.get(Calendar.MONTH))
				|| (toCompare.get(Calendar.YEAR) == base.get(Calendar.YEAR)
						&& toCompare.get(Calendar.MONTH) == base
								.get(Calendar.MONTH) && toCompare
						.get(Calendar.DAY_OF_MONTH) > base
						.get(Calendar.DAY_OF_MONTH)))
		{
			result = -1;
		}
		else if (toCompare.get(Calendar.YEAR) == base.get(Calendar.YEAR)
				&& toCompare.get(Calendar.MONTH) == base.get(Calendar.MONTH)
				&& toCompare.get(Calendar.DAY_OF_MONTH) == base
						.get(Calendar.DAY_OF_MONTH))
		{
			result = 0;
		}
		else
		{
			result = 1;
		}
		
		return result;
	}
	
	public List<Task> sort(final int colIndex, final boolean sortDown)
	{
		getTasks();
		
		if(colIndex >= 0 && colIndex <= 6)
		{
			Collections.sort(tasks, new Comparator<Task>()
			{
				@Override
				public int compare(Task arg0, Task arg1)
				{
					
					int result = 0;
					if (colIndex == 1 || colIndex == 2 || colIndex == 3
							|| colIndex == 5 || colIndex == 6)
					{
						StringComparer stringComparator = new StringComparer(
								sortDown);
						result = comparer(stringComparator, arg0, arg1, colIndex);
					}
					else if (colIndex == 0)
					{
						IntegerComparer intComparator = new IntegerComparer(
								sortDown);
						result = comparer(intComparator, arg0, arg1, colIndex);
					}
					else if (colIndex == 4)
					{
						DateComparer dateComparator = new DateComparer(sortDown);
						result = comparer(dateComparator, arg0, arg1, colIndex);
					}
					return result;
				}
			});
		}
		else
		{
			throw new IllegalArgumentException("Invalid Column index.");
		}
		
		return tasks;
	}
	
	public int comparer(Comparer comparer, Task taskX, Task taskY, int colIndex)
	{
		int result = 0;
		
		if (colIndex == 0)
		{
			result = comparer.compare(taskX.getTaskID(), taskY.getTaskID());
		}
		else if (colIndex == 1)
		{
			result = comparer.compare(taskX.getTitle().toUpperCase(), taskY
					.getTitle().toUpperCase());
		}
		else if (colIndex == 2)
		{
			result = comparer.compare(taskX.getAssignedTo().getUserName()
					.toUpperCase(), taskY.getAssignedTo().getUserName()
					.toUpperCase());
		}
		else if (colIndex == 3)
		{
			result = comparer.compare(taskX.getStatus().toString(), taskY
					.getStatus().toString());
		}
		else if (colIndex == 4)
		{
			result = comparer.compare(taskX.getDueDate(), taskY.getDueDate());
		}
		else if (colIndex == 5)
		{
			result = comparer.compare(taskX.getPriority().toString(), taskY
					.getPriority().toString());
		}
		else if (colIndex == 6)
		{
			result = comparer.compare(taskX.getDescription().toUpperCase(),
					taskY.getDescription().toUpperCase());
		}
		return result;
	}
}