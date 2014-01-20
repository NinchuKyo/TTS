package tts.application;

import tests.persistence.DataAccessStub;
import tts.presentation.*;

public class Main
{
	public static final String dbName = "TEST";
	public static final String dbNameReal = "SC";
	
	public static void main(String[] args)
	{
		startUp();
		//new LogInWindow();
		shutDown();
	}
	
	public static void startUp()
	{
		Services.createDataAccess();
	}
	
	public static void startUpStubDB()
	{
		Services.createDataAccess(new DataAccessStub());
	}
	
	public static void shutDown()
	{
		Services.closeDataAccess();
	}
	
}
