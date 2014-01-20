package tests.integration;

import junit.framework.TestCase;
import tts.application.Services;
import tests.persistence.TestDataAccessStub;

public class TestDataAccessHSQLDB extends TestCase
{
	
	public TestDataAccessHSQLDB(String arg0)
	{
		super(arg0);
	}

	public void testDataAccess()
	{
		
		Services.closeDataAccess();

		System.out.println("\nStarting Integration test DataAccess (using default DB)");
		
		// Use the following two statements to run with the real database
		Services.createDataAccess();
		
		TestDataAccessStub.allTests();

		System.out.println("Finished Integration test DataAccess (using default DB)");
	}
}