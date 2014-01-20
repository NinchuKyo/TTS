package tts.persistence;

import java.util.List;

import tts.objects.Task;
import tts.objects.User;

public interface DataAccess
{	
	public void open();
	
	public void close();
	
	public List<User> getUsersSequential();
	
	public boolean insertUser(User newUser);
	
	public List<Task> getTasksSequential();
	
	public boolean insertTask(Task newTask);
	
	public boolean updateTask(Task currentTask);
	
	public boolean deleteTask(Task currentTask);
	
	public void select(String source);
	
	public String lookup(String key);
}