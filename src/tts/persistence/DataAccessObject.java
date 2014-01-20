package tts.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLWarning;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import tts.objects.*;

public class DataAccessObject implements DataAccess
{
	private Statement statement1, statement2;
	private Connection connection;
	private ResultSet resultSet1, resultSet2, resultSet3;
	
	private String dbName;
	private String dbType;
	
	private List<User> users;
	private List<Task> tasks;
	
	private String cmdString;
	private int countUpdate;
	private final static String EOF = "  ";
	
	public DataAccessObject(String dbName)
	{
		this.dbName = dbName;
	}
	
	public void open()
	{
		String url;
		
		try
		{
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:database/" + dbName;
			connection = DriverManager.getConnection(url, "SA", "");
			statement1 = connection.createStatement();
			statement2 = connection.createStatement();
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		System.out.println("Opened " + dbType + " database " + dbName);
		
	}
	
	public void close()
	{
		try
		{
			cmdString = "shutdown compact";
			resultSet1 = statement1.executeQuery(cmdString);
			connection.close();
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		System.out.println("Closed " + dbType + " database " + dbName);
	}
	
	public List<User> getUsersSequential()
	{
		User user;
		String userName = EOF;
		int userID;
		int maxUserID = 0;
		
		users = new ArrayList<User>();
		
		try
		{
			cmdString = "SELECT * FROM USERS";
			resultSet1 = statement1.executeQuery(cmdString);
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		try
		{
			while (resultSet1.next())
			{
				userID = resultSet1.getInt("UserID");
				userName = resultSet1.getString("UserName");
				user = new User(userName);
				user.setUserID(userID);
				users.add(user);
				
				if (userID > maxUserID)
				{
					maxUserID = userID;
				}
				
			}
			resultSet1.close();
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e) + " in getUsersSequential()");
		}
		
		// make sure that we have unique userID when we are creating new users
		User.setNextUserID(maxUserID + 1);
		
		return users;
		
	}
	
	public boolean insertUser(User newUser)
	{
		String values;
		boolean result = false;
		
		try
		{
			values = newUser.getUserID() + ", '"
					+ formatString(newUser.getUserName()) + "'";
			cmdString = "Insert into Users " + " Values(" + values + ")";
			countUpdate = statement1.executeUpdate(cmdString);
			result = (checkWarning(statement1, countUpdate) == null);
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e) + " in insertUser()");
		}
		
		return result;
	}
	
	public List<Task> getTasksSequential()
	{
		User tempAssignedToUser;
		User tempCreatedUser;
		
		Task task;
		tasks = new ArrayList<Task>();
		
		int maxTaskID = 0;
		
		String title, description, comments, priority, status;
		int taskID, createdID, assignedToID;
		double timeEstimate, timeSpent;
		
		title = EOF;
		description = EOF;
		comments = EOF;
		priority = EOF;
		status = EOF;
		
		try
		{
			cmdString = "SELECT * FROM TASKS";
			resultSet2 = statement2.executeQuery(cmdString);
			while (resultSet2.next())
			{
				Calendar cDate = Calendar.getInstance();
				Calendar dDate = Calendar.getInstance();
				Calendar uDate = Calendar.getInstance();
				
				taskID = resultSet2.getInt("TaskID");
				title = resultSet2.getString("title");
				createdID = resultSet2.getInt("createdID");
				assignedToID = resultSet2.getInt("assignedToID");
				description = resultSet2.getString("description");
				comments = resultSet2.getString("comments");
				timeEstimate = resultSet2.getDouble("timeEstimate");
				timeSpent = resultSet2.getDouble("timeSpent");
				priority = resultSet2.getString("priority");
				status = resultSet2.getString("status");
				resultSet2.getDate("createdDate", cDate);
				resultSet2.getDate("dueDate", dDate);
				resultSet2.getDate("updatedDate", uDate);
				
				tempAssignedToUser = getUserByID(assignedToID);
				tempCreatedUser = getUserByID(createdID);
				
				task = new Task(title, tempCreatedUser, tempAssignedToUser);
				task.setTaskID(taskID);
				task.setDescription(description);
				task.setComments(comments);
				task.setPriority(PriorityCode.valueOf(priority));
				task.setStatus(StatusCode.valueOf(status));
				task.setTimeSpent(timeSpent);
				task.setTimeEstimate(timeEstimate);
				task.setCreatedDate(cDate);
				task.setDueDate(dDate);
				task.setUpdatedDate(uDate);
				
				if (taskID > maxTaskID)
				{
					maxTaskID = taskID;
				}
				
				tasks.add(task);
			}
			resultSet2.close();
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e) + " in getTasksSequential()");
		}
		
		// set static ID in task object to a value higher than current task IDs
		// stored in DB
		Task.setNextTaskID(maxTaskID + 1);
		
		return tasks;
	}
	
	public boolean insertTask(Task newTask)
	{
		String values;
		boolean result = false;
		
		try
		{
			values = newTask.getTaskID() + ", '"
					+ formatString(newTask.getTitle()) + "', "
					+ newTask.getCreator().getUserID() + ", "
					+ newTask.getAssignedTo().getUserID() + ", '"
					+ formatString(newTask.getDescription()) + "', '"
					+ formatString(newTask.getComments()) + "', "
					+ newTask.getTimeEstimate() + ", " + newTask.getTimeSpent()
					+ ", '" + newTask.getPriorityString() + "', '"
					+ newTask.getStatusString() + "', '"
					+ formatDate(newTask.getCreatedDate()) + "', '"
					+ formatDate(newTask.getDueDate()) + "', '"
					+ formatDate(newTask.getUpdatedDate()) + "'";
			cmdString = "Insert into Tasks " + " Values(" + values + ")";
			countUpdate = statement2.executeUpdate(cmdString);
			result = (checkWarning(statement2, countUpdate) == null);
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		return result;
	}
	
	public boolean updateTask(Task currentTask)
	{
		boolean result = false;
		
		try
		{
			result = (deleteTask(currentTask) && insertTask(currentTask));
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		return result;
	}
	
	public boolean deleteTask(Task currentTask)
	{
		String values;
		boolean result = false;
		try
		{
			values = currentTask.getTaskID() + "";
			cmdString = "Delete from Tasks where TaskID=" + values;
			countUpdate = statement2.executeUpdate(cmdString);
			result = (checkWarning(statement2, countUpdate) == null);
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		return result;
	}
	
	private User getUserByID(int userID)
	{
		User user = new User("null");
		String userName = EOF;
		int newUserID;
		
		try
		{
			cmdString = "SELECT * FROM USERS WHERE userID=" + userID;
			resultSet3 = statement1.executeQuery(cmdString);
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e));
		}
		
		try
		{
			while (resultSet3.next())
			{
				newUserID = resultSet3.getInt("UserID");
				userName = resultSet3.getString("UserName");
				user = new User(userName);
				user.setUserID(newUserID);
			}
			resultSet3.close();
		}
		catch (Exception e)
		{
			System.out.println(processSQLError(e) + " in getUsersByID");
		}
		
		return user;
	}
	
	private static String formatDate(Calendar date)
	{
		// Date in SQL format
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date.getTime());
	}
	
	private static String formatString(String str)
	{
		// Strings containing ' formatted to '' to allow inserting
		// into SQL database
		String result = "";
		
		if (str != null)
		{
			result = str.replaceAll("'", "''");
		}
		
		return result;
		
	}
	
	private String checkWarning(Statement statement, int count)
	{
		String result = null;
		try
		{
			SQLWarning warning = statement.getWarnings();
			if (warning != null)
			{
				result = warning.getMessage();
			}
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		
		if (count != 1)
		{
			result = "Tuple not inserted correctly!";
		}
		
		return result;
		
	}
	
	private String processSQLError(Exception e)
	{
		return "---SQL Error: " + e.getMessage();
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
