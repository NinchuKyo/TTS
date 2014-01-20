package tests;

import tests.integration.TestIntegration;
import junit.framework.Test;
import junit.framework.TestSuite;

public class RunIntegrationTests
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("Integration tests");
		suite.addTest(TestIntegration.suite());
		return suite;
	}
}
