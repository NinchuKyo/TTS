package tts.objects;

public enum StatusCode
{
	CREATED, IN_PROGRESS, ON_HOLD, COMPLETED, CANCELLED;
	
	public static String[] enumToStringArray()
	{
		String[] result = new String[StatusCode.values().length];
		int count = 0;
		
		for (StatusCode code : StatusCode.values())
		{
			result[count] = code.toString();
			count++;
		}
		
		return result;
		
	}
	
}
