package tts.business;

import java.util.Calendar;

public abstract class Comparer {
	
	protected boolean sortDown;
	
	public Comparer(boolean sortDown)
	{
		this.sortDown = sortDown;
	}
		
	public int compare(int firstInt, int secondInt)
	{
		return 0;
	}
	
	public int compare(Calendar firstDate, Calendar secondDate)
	{
		return 0;
	}
	
	public int compare(String firstString, String secondString)
	{
		return 0;
	}
}
