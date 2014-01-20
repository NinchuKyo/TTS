package tts.presentation;

import java.util.List;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import tts.objects.PriorityCode;
import tts.objects.StatusCode;
import tts.objects.User;

public class StaticWindowMethods
{
	public static void centerShell(Shell shell, Display display)
	{
		Monitor primary = display.getPrimaryMonitor();
	    Rectangle bounds = primary.getBounds();
	    Rectangle rect = shell.getBounds();
	    
	    int x = bounds.x + (bounds.width - rect.width) / 2;
	    int y = bounds.y + (bounds.height - rect.height) / 2;
	    
	    shell.setLocation(x, y);
	}
	
	public static void populateAssignedToDropDown(Combo cboxAssignedTo, List<User> listOfUsers)
	{
		for (User u : listOfUsers)
		{
			cboxAssignedTo.add(u.getUserName());
		}
	}
	
	public static void populatePriorityDropDown(Combo cboxPriority)
	{
		cboxPriority.setItems(PriorityCode.enumToStringArray());
	}
	
	public static void populateStatusDropDown(Combo cboxStatus)
	{
		cboxStatus.setItems(StatusCode.enumToStringArray());
	}
}
