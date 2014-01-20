package tts.presentation;

import org.eclipse.swt.widgets.TableItem;

public class EditTaskWindow extends TaskWindow
{
	public EditTaskWindow()
	{
		super("Edit a task");
	}
	
	public EditTaskWindow(TableItem item)
	{
		super("Edit a task", item);
	}
	
	public void setDropDownValues()
	{
		//TODO fill drop downs with current tasks fields
	}
}
