package tts.business;

import java.util.Calendar;

public class DateComparer extends Comparer
{

	public DateComparer(boolean sortDown) {
		super(sortDown);
	}

	public int compare(Calendar firstDate, Calendar secondDate)
	{
		if(super.sortDown)
		{
			return firstDate.compareTo(secondDate);
			
		}
		else
		{
			return secondDate.compareTo(firstDate);			
		}
	}
}
