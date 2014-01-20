package tts.business;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.eclipse.swt.widgets.DateTime;

public class FormatDate
{
	public static Calendar convertDateTimeToCalendar(DateTime date)
	{
		Calendar cal = null;
		
		if(date != null)
		{
			cal = Calendar.getInstance();
			cal.set(date.getYear(), date.getMonth(), date.getDay());			
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		return cal;
	}
	
	public static String formatDate(Calendar date)
	{
		return formatDate(date, null);
	}
	
	public static String formatDate(Calendar date, Locale locale)
	{
		String result = "";
		SimpleDateFormat formatter;
		String pattern;
		if (locale == null)
		{
			formatter = new SimpleDateFormat();
			pattern = formatter.toLocalizedPattern();
		}
		else
		{
			formatter = (SimpleDateFormat) SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT, locale);
			pattern = formatter.toPattern();
		}
		
		pattern = pattern.substring(0, pattern.lastIndexOf("y") + 1);
		while (pattern.lastIndexOf("y") + 1 - pattern.indexOf("y") < 4)
		{
			pattern = pattern + "y";
		}
		
		formatter.applyPattern(pattern);
		
		if(date != null)
		{			
			result = formatter.format(date.getTime());
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		return result;
	}
}
