package tests.persistence;

import junit.framework.Test;
import junit.framework.TestSuite;

public class TestPersistence
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("Database Test");
		suite.addTestSuite(TestDataAccessStub.class);
		return suite;
	}
	
}
