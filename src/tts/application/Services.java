package tts.application;

import java.util.ArrayList;

import tts.persistence.DataAccess;
import tts.persistence.DataAccessObject;

public class Services
{
	private static DataAccess dataAccessService = null;
	
	public static DataAccess createDataAccess(DataAccess otherDataAccessService)
	{
		if (dataAccessService == null)
		{
			dataAccessService = otherDataAccessService;
			dataAccessService.open();
		}
		return dataAccessService;
	}
	
	public static DataAccess createDataAccess()
	{
		if (dataAccessService == null)
		{
			dataAccessService = new DataAccessObject(Main.dbNameReal);
			dataAccessService.open();
		}
		return dataAccessService;
	}
	
	public static DataAccess getDataAccess()
	{
		if (dataAccessService == null)
		{
			System.out
					.println("Connection to data access has not been established.");
			System.exit(1);
		}
		return dataAccessService;
	}
	
	public static ArrayList<String> processQuery(String source, ArrayList<String> keys)
	{
		ArrayList<String> result = new ArrayList<String>();
		String answer;
		
		dataAccessService.select(source);
		for (String key : keys)
		{
			answer = dataAccessService.lookup(key);
			if (answer != null)
				result.add(answer);
		}
		
		return result;
	}
	
	public static void closeDataAccess()
	{
		if (dataAccessService != null)
		{
			dataAccessService.close();
		}
		dataAccessService = null;
	}
}
