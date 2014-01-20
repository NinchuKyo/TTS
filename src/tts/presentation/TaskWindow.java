package tts.presentation;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import tts.objects.*;
import tts.business.*;

public abstract class TaskWindow
{
	
	protected Shell shlTaskTrackingSystem;
	private Text txtTitle;
	private Text txtTimeEstimate;
	private Text txtTimeSpent;
	private Text txtDescription;
	private Text txtComments;
	protected Combo cboxAssignedTo;
	protected Combo cboxStatus;
	protected Combo cboxPriority;
	private Label lblCreatedByField;
	private Label lblCreatedDateField;
	private DateTime dueDate;
	private String title;
	protected AccessUsers users;
	
	public TaskWindow()
	{
		title = "Create a task";
		users = new AccessUsers();
		createContents();
		open();
	}
	
	public TaskWindow(String title)
	{
		this.title = title;
		users = new AccessUsers();
		createContents();
		open();
	}
	
	public TaskWindow(String title, TableItem selectedTask)
	{
		this.title = title;
		users = new AccessUsers();
		createContents();
		open();
	}
	
	/**
	 * Open the window.
	 */
	public void open()
	{
		Display display = Display.getDefault();
		createContents();
		shlTaskTrackingSystem.open();
		shlTaskTrackingSystem.layout();
		while (!shlTaskTrackingSystem.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents()
	{
		shlTaskTrackingSystem = new Shell();
		shlTaskTrackingSystem.setSize(640, 480);
		shlTaskTrackingSystem.setText(title);
		
		Label lblTaskTitle = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblTaskTitle.setBounds(10, 13, 64, 15);
		lblTaskTitle.setText("Task Title *");
		
		txtTitle = new Text(shlTaskTrackingSystem, SWT.BORDER);
		txtTitle.setTextLimit(Task.MAX_TITLE_LENGTH);
		txtTitle.setBounds(90, 10, 524, 21);
		
		Button btnSave = new Button(shlTaskTrackingSystem, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (!isDouble(txtTimeEstimate.getText())
						|| !isDouble(txtTimeSpent.getText()))
				{
					MessageBox error = new MessageBox(shlTaskTrackingSystem);
					error.setMessage("Please enter a non-negative numeric value for Time Estimate and Time Spent");
					error.open();
				}
				else
				{
					save();
				}
			}
		});
		btnSave.setBounds(539, 407, 75, 25);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shlTaskTrackingSystem, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				shlTaskTrackingSystem.close();
			}
		});
		btnCancel.setBounds(458, 407, 75, 25);
		btnCancel.setText("Cancel");
		
		Label titleSeparator = new Label(shlTaskTrackingSystem, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		titleSeparator.setBounds(10, 34, 604, 2);
		
		Label lblCreatedBy = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblCreatedBy.setBounds(10, 45, 64, 15);
		lblCreatedBy.setText("Created By");
		
		cboxAssignedTo = new Combo(shlTaskTrackingSystem, SWT.READ_ONLY);
		cboxAssignedTo.setBounds(90, 71, 195, 23);
		
		Label lblAssignedTo = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblAssignedTo.setText("Assigned To");
		lblAssignedTo.setBounds(10, 74, 75, 15);
		
		Label lblCreatedDate = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblCreatedDate.setBounds(312, 45, 75, 15);
		lblCreatedDate.setText("Created Date");
		
		lblCreatedByField = new Label(shlTaskTrackingSystem, SWT.BORDER
				| SWT.SHADOW_IN);
		lblCreatedByField.setBounds(90, 45, 195, 15);
		lblCreatedByField.setText(AccessUsers.getLoggedInUser().getUserName());
		
		lblCreatedDateField = new Label(shlTaskTrackingSystem, SWT.BORDER
				| SWT.SHADOW_IN);
		lblCreatedDateField.setBounds(458, 45, 156, 15);
		lblCreatedDateField.setText((new SimpleDateFormat("dd/MM/yyyy"))
				.format(new Date()));
		
		Label lblDueDate = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblDueDate.setText("Due Date");
		lblDueDate.setBounds(312, 74, 75, 15);
		
		dueDate = new DateTime(shlTaskTrackingSystem, SWT.BORDER);
		dueDate.setBounds(458, 66, 156, 24);
		
		cboxStatus = new Combo(shlTaskTrackingSystem, SWT.READ_ONLY);
		cboxStatus.setBounds(90, 100, 195, 23);
		
		Label lblStatus = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblStatus.setText("Status");
		lblStatus.setBounds(10, 103, 75, 15);
		
		Label lblAmountOfWork = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblAmountOfWork.setBounds(312, 132, 140, 15);
		lblAmountOfWork.setText("Current Time Spent");
		
		txtTimeEstimate = new Text(shlTaskTrackingSystem, SWT.BORDER);
		txtTimeEstimate.setText("0");
		txtTimeEstimate.setBounds(457, 129, 157, 21);
		
		cboxPriority = new Combo(shlTaskTrackingSystem, SWT.READ_ONLY);
		cboxPriority.setBounds(90, 129, 195, 23);
		
		Label lblPriority = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblPriority.setBounds(10, 132, 55, 15);
		lblPriority.setText("Priority");
		
		Label lblTimeEstimate = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblTimeEstimate.setBounds(312, 103, 87, 15);
		lblTimeEstimate.setText("Time Estimate");
		
		txtTimeSpent = new Text(shlTaskTrackingSystem, SWT.BORDER);
		txtTimeSpent.setBounds(458, 100, 156, 21);
		
		txtDescription = new Text(shlTaskTrackingSystem, SWT.MULTI | SWT.BORDER
				| SWT.WRAP | SWT.V_SCROLL);
		txtDescription.setTextLimit(Task.MAX_TEXT_LENGTH);
		txtDescription.setBounds(10, 183, 275, 210);
		
		txtComments = new Text(shlTaskTrackingSystem, SWT.MULTI | SWT.BORDER
				| SWT.WRAP | SWT.V_SCROLL);
		txtComments.setTextLimit(Task.MAX_TEXT_LENGTH);
		txtComments.setBounds(312, 183, 302, 210);
		
		Label lblDescription = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblDescription.setBounds(10, 162, 64, 15);
		lblDescription.setText("Description");
		
		Label lblComments = new Label(shlTaskTrackingSystem, SWT.NONE);
		lblComments.setBounds(312, 162, 75, 15);
		lblComments.setText("Comments");
		
		Label bottomSeparator = new Label(shlTaskTrackingSystem, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		bottomSeparator.setBounds(10, 399, 604, 2);
		
		setDropDownValues();
	}
	
	protected abstract void setDropDownValues();
	
	private boolean isDouble(String input)
	{
		boolean result = false;
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
		return result;
	}
	
	private void save()
	{
		//TODO remove comments abstract?
		AccessTasks worker = new AccessTasks();
		String title = txtTitle.getText();
		User creator = null;
		User assignedTo = null;
		
		for (User u : users.getUsers())
		{
			if (u.getUserName().equals(lblCreatedByField.getText()))
			{
				creator = u;
			}
			if (u.getUserName().equals(cboxAssignedTo.getText()))
			{
				assignedTo = u;
			}
		}
		Task task = new Task(title, creator, assignedTo);
		task.setComments("");
		task.setDescription("");
		task.setDueDate(new Date());
		task.setPriority(PriorityCode.UNDETERMINED);
		task.setStatus(StatusCode.CREATED);
		task.setTimeEstimate(0);
		task.setTimeSpent(0);
		
		worker.getTasks();
		worker.addTask(task);
		MessageBox success = new MessageBox(shlTaskTrackingSystem);
		success.setMessage("Save succeeded");
		success.open();
		
		shlTaskTrackingSystem.close();
	}
}
