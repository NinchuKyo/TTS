package tts.business;

public class IntegerComparer extends Comparer
{

	public IntegerComparer(boolean sortDown) {
		super(sortDown);
	}

	public int compare(int firstInt, int secondInt)
	{
		if(super.sortDown)
		{
			return firstInt - secondInt;
			
		}
		else
		{
			return secondInt - firstInt;			
		}
	}
}
