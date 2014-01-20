package tests.objects;

import junit.framework.TestCase;
import tts.objects.StatusCode;

public class TestStatusCode extends TestCase
{
	public TestStatusCode(String arg0)
	{
		super(arg0);
	}
	
	public void testEnumToStringArray()
	{
		System.out.println("\nStarting testEnumToStringArray: status codes");
		
		String[] testEnum = StatusCode.enumToStringArray();
		assertTrue(testEnum[0].equals("CREATED"));
		assertTrue(testEnum[1].equals("IN_PROGRESS"));
		assertTrue(testEnum[2].equals("ON_HOLD"));
		assertTrue(testEnum[3].equals("COMPLETED"));
		assertTrue(testEnum[4].equals("CANCELLED"));
		
		System.out.println("Finished testEnumToStringArray: status codes");
	}
	
}
