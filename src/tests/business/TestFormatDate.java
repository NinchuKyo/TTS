package tests.business;

import java.util.Calendar;
import java.util.Locale;

import tts.business.FormatDate;
import junit.framework.TestCase;

public class TestFormatDate extends TestCase
{
	private String date;
	private Calendar cal;
	
	public TestFormatDate(String arg0)
	{
		super(arg0);
		
		System.out.println("\nStarting test FormatDate");
	}
	
	public void setUp()
	{
	}
	
	public void testNull()
	{
		System.out.println("\nStarting testNull");
		
		try
		{
			FormatDate.convertDateTimeToCalendar(null);
			fail("Illegal Argument Exception expected");
		}
		catch (IllegalArgumentException iae)
		{
		}
		
		try
		{
			FormatDate.formatDate(null);
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
		
		cal = Calendar.getInstance();
		
		cal.set(1989, Calendar.JANUARY, 18);
		date = FormatDate.formatDate(cal, Locale.CANADA);
		assertEquals(date, "18/01/1989");
		
		cal.set(2013, Calendar.JUNE, 24);
		date = FormatDate.formatDate(cal, Locale.CANADA);
		assertEquals(date, "24/06/2013");
		
		cal.set(2013, Calendar.JANUARY, 1);
		date = FormatDate.formatDate(cal, Locale.CANADA);
		assertEquals(date, "01/01/2013");
		
		cal.set(2013, Calendar.DECEMBER, 31);
		date = FormatDate.formatDate(cal, Locale.CANADA);
		assertEquals(date, "31/12/2013");
		
		System.out.println("Finished testBorderCases");
	}
}
