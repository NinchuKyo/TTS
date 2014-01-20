package tests.business;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestBusiness
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("Business tests");
		suite.addTestSuite(TestAccessUsers.class);
		suite.addTestSuite(TestAccessTasks.class);
		return suite;
	}
}
