package tts.business;

import java.util.List;

import tts.application.Main;
import tts.application.Services;
import tts.objects.Task;
import tts.persistence.DataAccessStub;

public class AccessTasks
{
	private DataAccessStub dataAccess;
	private List<Task> tasks;
	
	public AccessTasks()
	{
		dataAccess = (DataAccessStub) Services.getDataAccess(Main.dbName);
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
	
	public void editTask(Task toUpdate)
	{
		if (toUpdate != null)
		{
			int index = tasks.indexOf(toUpdate);
			if (index >= 0)
			{
				tasks.set(index, toUpdate);
			}
			
			dataAccess.updateTask(toUpdate);
		}
		else
		{
			throw new IllegalArgumentException("Cannot edit null!");
		}
	}
	
	public void addTask(Task toAdd)
	{
		if (toAdd != null && tasks.indexOf(toAdd) < 0)
		{
			tasks.add(toAdd);
			dataAccess.insertTask(toAdd);
		}
		else
		{
			throw new IllegalArgumentException("Cannot add null!");
		}
	}
	
	public void deleteTask(Task toDelete)
	{
		if (toDelete != null)
		{
			int index = tasks.indexOf(toDelete);
			tasks.remove(index);
			dataAccess.deleteTask(toDelete);
		}
		else
		{
			throw new IllegalArgumentException("Cannot delete null!");
		}
	}
	
}
