package tests.business;

import tts.business.Validate;
import junit.framework.TestCase;

public class TestValidate extends TestCase
{
	private boolean result;
	
	public TestValidate(String arg0)
	{
		super(arg0);
		
		System.out.println("\nStarting test Validate");
	}
	
	public void setUp()
	{
	}
	
	public void testNull()
	{
		System.out.println("\nStarting testNull");
		
		try
		{
			Validate.isPositiveDouble(null);
			fail("Illegal Argument Exception expected");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		System.out.println("Finished testNull");
	}
	
	
	public void testBorderCases()
	{
		System.out.println("\nStarting testBorderCases");
		
		result = Validate.isPositiveDouble("0.0");
		assertTrue(result);
		result = Validate.isPositiveDouble("-0.0");
		assertTrue(result);
		result = Validate.isPositiveDouble("0.0000001");
		assertTrue(result);
		result = Validate.isPositiveDouble("-0.0000001");
		assertFalse(result);
		result = Validate.isPositiveDouble("999999999999");
		assertTrue(result);
		result = Validate.isPositiveDouble("-999999999999");
		assertFalse(result);
		result = Validate.isPositiveDouble("abc");
		assertFalse(result);
		result = Validate.isPositiveDouble("8.99g");
		assertFalse(result);
		result = Validate.isPositiveDouble("_0.56");
		assertFalse(result);
		
		System.out.println("Finished testBorderCases");
	}
}

