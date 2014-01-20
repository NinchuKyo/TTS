package tests.objects;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestObjects
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("Object tests");
		suite.addTestSuite(TestUser.class);
		suite.addTestSuite(TestTask.class);
		suite.addTestSuite(TestPriorityCode.class);
		suite.addTestSuite(TestStatusCode.class);
		return suite;
	}
	
}
