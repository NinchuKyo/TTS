package tests.business;

import java.util.Calendar;

import junit.framework.TestCase;
import tts.business.DateComparer;
import tts.business.IntegerComparer;
import tts.business.StringComparer;


public class TestComparators extends TestCase{

	private StringComparer sComparer;
	private IntegerComparer iComparer;
	private DateComparer dComparer;
	
	public TestComparators(String arg0)
	{
		super(arg0);
		
		System.out.println("\nStarting test TestComparators");
	}
	
	public void setUp()
	{	
		sComparer = new StringComparer(false);
		iComparer = new IntegerComparer(false);
		dComparer = new DateComparer(false);
	}
	
	public void testEmptyString()
	{	
		System.out.println("\nStarting TestEmptyString");
		
		assertEquals(sComparer.compare("", ""), 0);		
		assertTrue(sComparer.compare("a", "") < 0);
		assertTrue(sComparer.compare("", "A") > 0);
		assertTrue(sComparer.compare("7", "") < 0);
		
		sComparer = new StringComparer(true);
		
		assertEquals(sComparer.compare("", ""), 0);		
		assertTrue(sComparer.compare("b", "") > 0);
		assertTrue(sComparer.compare("", "c") < 0);
		assertTrue(sComparer.compare("7", "") > 0);		
		
		System.out.println("\nFinished TestEmptyString");
	}
	
	public void testEqualStrings()
	{
		System.out.println("\nStarting TestEqualString");
		
		assertEquals(sComparer.compare("same", "same"), 0);
		assertEquals(sComparer.compare("Same", "Same"), 0);
		assertEquals(sComparer.compare("ABCdefGHIJKL", "ABCdefGHIJKL"), 0);
		assertEquals(sComparer.compare("a", "a"), 0);
				
		assertEquals(sComparer.compare("afdafdafaea fj;lkajdf ;jakdjf kal;jdsflkaj kl;fja;iwhf", "afdafdafaea fj;lkajdf ;jakdjf kal;jdsflkaj kl;fja;iwhf"), 0);
	
		System.out.println("\nFinished TestEqualString");
	}
	
	public void testUnequalStrings()
	{
		System.out.println("\nStarting TestUnequalString");
		
		assertTrue(sComparer.compare("Same", "same") > 0);
		assertTrue(sComparer.compare("same", "Same") < 0);
		assertTrue(sComparer.compare("ABCdefGHIJKl", "ABCdefGHIJKL") < 0);
				
		assertTrue(sComparer.compare("afdafdafaexxfj;lkajdf ;jakdjf kal;jdsflkaj kl;fja;iwhf", "afdafdafaea fj;lkajdf ;jakdjf kal;jdsflkaj kl;fja;iwhf") < 0);
	
		sComparer = new StringComparer(true);
		assertTrue(sComparer.compare("Same", "same") < 0);
		assertTrue(sComparer.compare("same", "Same") > 0);
		assertTrue(sComparer.compare("ABCdefGHIJKl", "ABCdefGHIJKL") > 0);
				
		assertTrue(sComparer.compare("afdafdafaexxfj;lkajdf ;jakdjf kal;jdsflkaj kl;fja;iwhf", "afdafdafaea fj;lkajdf ;jakdjf kal;jdsflkaj kl;fja;iwhf") > 0);
	
		System.out.println("\nFinished TestUnequalString");
	}
	
	public void testIntegers()
	{
		System.out.println("\nStarting TestIntegers");
		
		assertTrue(iComparer.compare(4, 7) > 0);
		assertEquals(iComparer.compare(4, 7), 3);
		assertTrue(iComparer.compare(10, 7) < 0);
		assertTrue(iComparer.compare(-1000, 5) > 0);
		assertEquals(iComparer.compare(-1000, 5),  1005);
		assertTrue(iComparer.compare(5, -1000) < 0);
		assertEquals(iComparer.compare(0, -1), -1);
		assertEquals(iComparer.compare(-1, 0), 1);
		assertEquals(iComparer.compare(0,0), 0);
		assertTrue(iComparer.compare(55, 100) > 0);
		assertTrue(iComparer.compare(595, 54) < 0);
		assertTrue(iComparer.compare(35, 46) > 0);
		assertTrue(iComparer.compare(25, 15) < 0);
		
		iComparer = new IntegerComparer(true);
		
		assertTrue(iComparer.compare(2, 3) < 0);
		assertEquals(iComparer.compare(1, 2), -1);
		assertTrue(iComparer.compare(7, 1) > 0);		
		assertEquals(iComparer.compare(-1, -1), 0);
		assertTrue(iComparer.compare(-5, -7) > 0);
		assertTrue(iComparer.compare(-1000, 5) < 0);
		assertEquals(iComparer.compare((0-5), 0), -5);
		assertEquals(iComparer.compare(0, -6), 6);
		assertEquals(iComparer.compare(0,0), 0);
		assertTrue(iComparer.compare(55, 100) < 0);
		assertTrue(iComparer.compare(595, 54) > 0);
		assertTrue(iComparer.compare(35, 46) < 0);
		assertTrue(iComparer.compare(25, 15) > 0);
		
		iComparer = new IntegerComparer(false);
		
		assertTrue(iComparer.compare(Integer.MAX_VALUE, 0) < 0);
		assertTrue(iComparer.compare(0, Integer.MAX_VALUE) > 0);
		assertEquals(iComparer.compare(Integer.MAX_VALUE, Integer.MAX_VALUE), 0);
		assertTrue(iComparer.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) < 0);
		assertTrue(iComparer.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) > 0);
		assertEquals(iComparer.compare(-1, Integer.MAX_VALUE), Integer.MIN_VALUE);
		
		iComparer = new IntegerComparer(true);
		
		assertTrue(iComparer.compare(Integer.MAX_VALUE, 0) > 0);
		assertTrue(iComparer.compare(0, Integer.MAX_VALUE) < 0);
		assertEquals(iComparer.compare(Integer.MAX_VALUE, Integer.MAX_VALUE), 0);
		assertTrue(iComparer.compare(Integer.MIN_VALUE, Integer.MAX_VALUE) > 0);
		assertTrue(iComparer.compare(Integer.MAX_VALUE, Integer.MIN_VALUE) < 0);
		assertEquals(iComparer.compare(-1, Integer.MIN_VALUE), Integer.MAX_VALUE);	
		
		System.out.println("\nFinished TestIntegers");
	}
	
	public void testDates()
	{
		System.out.println("\nStarting TestDates");
		
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal1.set(1990,Calendar.JANUARY, 20);
		cal2.set(1990, Calendar.JANUARY, 21);
		
		assertTrue(dComparer.compare(cal1, cal2) > 0);
		assertTrue(dComparer.compare(cal2, cal1) < 0);
				
		cal1.set(1998,Calendar.AUGUST, 20);
		cal2.set(1990, Calendar.JANUARY, 21);
		
		assertTrue(dComparer.compare(cal1, cal2) < 0);
		assertTrue(dComparer.compare(cal2, cal1) > 0);
		
		cal1.set(2013,Calendar.AUGUST, 20);
		cal2.set(2010, Calendar.JANUARY, 21);
		
		assertTrue(dComparer.compare(cal1, cal2) < 0);
		assertTrue(dComparer.compare(cal2, cal1) > 0);
		
		dComparer = new DateComparer(true);
		
		cal1.set(1996,Calendar.JANUARY, 20);
		cal2.set(1996, Calendar.JANUARY, 21);
		
		assertTrue(dComparer.compare(cal1, cal2) < 0);
		assertTrue(dComparer.compare(cal2, cal1) > 0);
				
		cal1.set(1998,Calendar.AUGUST, 20);
		cal2.set(1990, Calendar.JANUARY, 21);
		
		assertTrue(dComparer.compare(cal1, cal2) > 0);
		assertTrue(dComparer.compare(cal2, cal1) < 0);
		
		cal1.set(2013,Calendar.DECEMBER, 20);
		cal2.set(2012, Calendar.JANUARY, 21);
		
		assertTrue(dComparer.compare(cal1, cal2) > 0);
		assertTrue(dComparer.compare(cal2, cal1) < 0);
		
		System.out.println("\nFinished TestDates");
	}
}
