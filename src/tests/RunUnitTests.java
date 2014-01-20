package tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import tests.objects.TestObjects;
import tests.persistence.TestPersistence;
import tests.business.TestBusiness;

public class RunUnitTests
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("Unit tests");
		suite.addTest(TestObjects.suite());
		suite.addTest(TestPersistence.suite());
		suite.addTest(TestBusiness.suite());
		return suite;
	}
}
