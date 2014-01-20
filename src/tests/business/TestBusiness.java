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
		suite.addTestSuite(TestFormatDate.class);
		suite.addTestSuite(TestValidate.class);
		suite.addTestSuite(TestComparators.class);
		return suite;
	}
}
