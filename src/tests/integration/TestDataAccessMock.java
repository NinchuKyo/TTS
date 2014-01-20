package tests.integration;

import java.util.ArrayList;
import org.hamcrest.Description;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.api.Action;
import org.jmock.api.Invocation;
import org.jmock.integration.junit3.MockObjectTestCase;

import tts.application.Services;
import tts.persistence.DataAccess;

public class TestDataAccessMock extends MockObjectTestCase
{
	public void testServices()
	{
		
		/*** Mock code ***/
		
		final String sourceName = "testSource";
		final Sequence querySequence = sequence("Query Sequence");
		
		// Set up the mock
		final DataAccess dataAccess = mock(DataAccess.class);
		ArrayList<String> results;
		
		// Declare expectations
		checking(new Expectations()
		{
			{
				one(dataAccess).open();
				inSequence(querySequence);
				will(printAction("mock db opened"));
				one(dataAccess).select(sourceName);
				inSequence(querySequence);
				will(printAction("mock db source selected"));
				allowing(dataAccess).lookup("abc");
				inSequence(querySequence);
				will(returnValue("xyz"));
				allowing(dataAccess).lookup("ghi");
				inSequence(querySequence);
				will(returnValue("uvw"));
				allowing(dataAccess).lookup(with(any(String.class)));
				inSequence(querySequence);
				will(returnValue(null));
				one(dataAccess).close();
				inSequence(querySequence);
				will(printAction("mock db closed"));
			}
		});
		
		/*** Test code ***/
		
		// Set up the test
		ArrayList<String> input = new ArrayList<String>();
		input.add("abc");
		input.add("mno");
		input.add("ghi");
		
		// Execute test code (not a complete test!)
		Services.createDataAccess(dataAccess);
		results = Services.processQuery(sourceName, input);
		assertEquals(2, results.size());
		assertEquals("xyz", results.get(0));
		assertEquals("uvw", results.get(1));
		Services.closeDataAccess();
	}
	
	public static Action printAction(final String message)
	{
		return new Action()
		{
			public void describeTo(Description description)
			{
				description.appendText("Open/Close action");
			}
			
			public Object invoke(Invocation invocation) throws Throwable
			{
				System.out.println("Testing Services with JMock: "
						+ message);
				return null;
			}
		};
	}
}
