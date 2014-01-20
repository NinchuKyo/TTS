package tests.integration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestIntegration
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("Integration tests");
		suite.addTestSuite(TestDataAccessMock.class);
		suite.addTestSuite(TestDataAccessHSQLDB.class);
		
		return suite;
	}
}
