package tts.business;

public class StringComparer extends Comparer{
	
	public StringComparer(boolean sortDown)
	{
		super(sortDown);
	}
	
	public int compare(String firstString, String secondString)
	{
		if(super.sortDown)
		{
			return firstString.compareTo(secondString);
			
		}
		else
		{
			return secondString.compareTo(firstString);			
		}
	}
}
