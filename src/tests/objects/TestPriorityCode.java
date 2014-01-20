package tests.objects;

import junit.framework.TestCase;
import tts.objects.PriorityCode;

public class TestPriorityCode extends TestCase
{
	public TestPriorityCode(String arg0)
	{
		super(arg0);
		System.out.println("\nStarting test PriorityCode");
	}
	
	public void testEnumToStringArray()
	{
		System.out.println("\nStarting testEnumToStringArray: priority codes");
		
		String[] testEnum = PriorityCode.enumToStringArray();
		assertTrue(testEnum[0].equals("UNDETERMINED"));
		assertTrue(testEnum[1].equals("LOW"));
		assertTrue(testEnum[2].equals("MEDIUM"));
		assertTrue(testEnum[3].equals("HIGH"));
		
		System.out.println("Finished testEnumToStringArray: priority codes");
	}
	
}
