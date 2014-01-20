package tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import tests.objects.TestObjects;
import tests.persistence.TestPersistence;
import tests.business.TestBusiness;

public class AllTests
{
	public static TestSuite suite;
	
	public static Test suite()
	{
		suite = new TestSuite("AllTests");
		testObjects();
		testPersistence();
		testBusiness();
		return suite;
	}
	
	private static void testObjects()
	{
		suite.addTest(TestObjects.suite());
	}
	
	private static void testPersistence()
	{
		suite.addTest(TestPersistence.suite());
	}
	
	private static void testBusiness()
	{
		suite.addTest(TestBusiness.suite());
	}
}
