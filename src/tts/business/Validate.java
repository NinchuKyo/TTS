package tts.business;

public class Validate
{
	public static boolean isPositiveDouble(String input)
	{
		boolean result = false;
		
		if(input != null)
		{
			try
			{
				double number = Double.parseDouble(input);
				if (number >= 0.00)
				{
					result = true;
				}
			}
			catch (NumberFormatException nfe)
			{
			}
		}
		else
		{
			throw new IllegalArgumentException();
		}
		
		return result;
	}
}
