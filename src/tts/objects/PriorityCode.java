package tts.objects;

public enum PriorityCode
{
	UNDETERMINED, LOW, MEDIUM, HIGH;
	
	public static String[] enumToStringArray()
	{
		String[] result = new String[PriorityCode.values().length];
		int count = 0;
		
		for (PriorityCode code : PriorityCode.values())
		{
			result[count] = code.toString();
			count++;
		}
		
		return result;
		
	}
	
}
