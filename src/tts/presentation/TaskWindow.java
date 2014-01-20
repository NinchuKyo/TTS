package tts.presentation;

import java.util.Calendar;

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

import acceptanceTests.Register;
import acceptanceTests.EventLoop;

public abstract class TaskWindow
{
	
	protected Shell shell;
	public Text txtTitle;
	public Text txtTimeSpent;
	public Text txtTimeEstimate;
	public Text txtDescription;
	public Text txtComments;
	public Combo cboxAssignedTo;
	public Combo cboxStatus;
	public Combo cboxPriority;
	public Label lblCreatedByField;
	public Label lblCreatedDateField;
	public DateTime dueDate;
	public TableItem item;
	protected AccessUsers users;
	public Button btnSave;
	public Button btnCancel;
	private String title;
	private Display display;
	
	public TaskWindow()
	{
		title = "Create a task";
		users = new AccessUsers();
		Register.newWindow(this);
		createContents();
		open();
	}
	
	public TaskWindow(String title)
	{
		this.title = title;
		users = new AccessUsers();
		display = Display.getDefault();
		Register.newWindow(this);
		createContents();
		StaticWindowMethods.centerShell(shell, display);
		open();
	}
	
	public TaskWindow(String title, TableItem item)
	{
		this.title = title;
		this.item = item;
		users = new AccessUsers();
		display = Display.getDefault();
		Register.newWindow(this);
		createContents();
		StaticWindowMethods.centerShell(shell, display);
		open();
	}
	
	/**
	 * Open the window.
	 */
	public void open()
	{
		shell.open();
		shell.layout();
		
		if(EventLoop.isEnabled())
		{
			while (!shell.isDisposed())
			{
				if (!display.readAndDispatch())
				{
					display.sleep();
				}
			}
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents()
	{
		shell = new Shell();
		shell.setSize(640, 480);
		shell.setText(title);
		
		Label lblTaskTitle = new Label(shell, SWT.NONE);
		lblTaskTitle.setBounds(10, 13, 64, 15);
		lblTaskTitle.setText("Task Title *");
		
		txtTitle = new Text(shell, SWT.BORDER);
		txtTitle.setTextLimit(Task.MAX_TITLE_LENGTH);
		txtTitle.setBounds(90, 10, 524, 21);
		
		btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if(txtTitle.getText().isEmpty() || txtTitle.getText().length() == 0 || txtTitle.getText() == null)
				{
					MyMessageBox error = new MyMessageBox(shell, "Task has no Title", "Please enter a Title for the task.", "OK");
					error.open();
				}
				
				else if (!Validate.isPositiveDouble(txtTimeSpent.getText())
						|| !Validate.isPositiveDouble(txtTimeEstimate.getText()))
				{
					MyMessageBox error = new MyMessageBox(shell, "Error", "Please enter a non-negative numeric value for Time Estimate and Time Spent.", "OK");
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
		
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				shell.close();
			}
		});
		btnCancel.setBounds(458, 407, 75, 25);
		btnCancel.setText("Cancel");
		
		Label titleSeparator = new Label(shell, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		titleSeparator.setBounds(10, 34, 604, 2);
		
		Label lblCreatedBy = new Label(shell, SWT.NONE);
		lblCreatedBy.setBounds(10, 45, 64, 15);
		lblCreatedBy.setText("Created By");
		
		cboxAssignedTo = new Combo(shell, SWT.READ_ONLY);
		cboxAssignedTo.setBounds(90, 71, 195, 23);
		
		Label lblAssignedTo = new Label(shell, SWT.NONE);
		lblAssignedTo.setText("Assigned To");
		lblAssignedTo.setBounds(10, 74, 75, 15);
		
		Label lblCreatedDate = new Label(shell, SWT.NONE);
		lblCreatedDate.setBounds(312, 45, 75, 15);
		lblCreatedDate.setText("Created Date");
		
		lblCreatedByField = new Label(shell, SWT.BORDER
				| SWT.SHADOW_IN);
		lblCreatedByField.setBounds(90, 45, 195, 15);
		lblCreatedByField.setText(AccessUsers.getLoggedInUser().getUserName());
		
		lblCreatedDateField = new Label(shell, SWT.BORDER
				| SWT.SHADOW_IN);
		lblCreatedDateField.setBounds(458, 45, 156, 15);
		lblCreatedDateField.setText(FormatDate.formatDate(Calendar.getInstance()));
		
		Label lblDueDate = new Label(shell, SWT.NONE);
		lblDueDate.setText("Due Date");
		lblDueDate.setBounds(312, 74, 75, 15);
		
		dueDate = new DateTime(shell, SWT.BORDER);
		dueDate.setBounds(458, 66, 156, 24);
		
		cboxStatus = new Combo(shell, SWT.READ_ONLY);
		cboxStatus.setBounds(90, 100, 195, 23);
		
		Label lblStatus = new Label(shell, SWT.NONE);
		lblStatus.setText("Status");
		lblStatus.setBounds(10, 103, 75, 15);
		
		Label lblAmountOfWork = new Label(shell, SWT.NONE);
		lblAmountOfWork.setBounds(312, 132, 140, 15);
		lblAmountOfWork.setText("Current Time Spent");
		
		txtTimeSpent = new Text(shell, SWT.BORDER);
		txtTimeSpent.setText("0");
		txtTimeSpent.setBounds(457, 129, 157, 21);
		
		cboxPriority = new Combo(shell, SWT.READ_ONLY);
		cboxPriority.setBounds(90, 129, 195, 23);
		
		Label lblPriority = new Label(shell, SWT.NONE);
		lblPriority.setBounds(10, 132, 55, 15);
		lblPriority.setText("Priority");
		
		Label lblTimeEstimate = new Label(shell, SWT.NONE);
		lblTimeEstimate.setBounds(312, 103, 87, 15);
		lblTimeEstimate.setText("Time Estimate");
		
		txtTimeEstimate = new Text(shell, SWT.BORDER);
		txtTimeEstimate.setText("0");
		txtTimeEstimate.setBounds(458, 100, 156, 21);
		
		txtDescription = new Text(shell, SWT.MULTI | SWT.BORDER
				| SWT.WRAP | SWT.V_SCROLL);
		txtDescription.setTextLimit(Task.MAX_TEXT_LENGTH);
		txtDescription.setBounds(10, 183, 275, 210);
		
		txtComments = new Text(shell, SWT.MULTI | SWT.BORDER
				| SWT.WRAP | SWT.V_SCROLL);
		txtComments.setTextLimit(Task.MAX_TEXT_LENGTH);
		txtComments.setBounds(312, 183, 302, 210);
		
		Label lblDescription = new Label(shell, SWT.NONE);
		lblDescription.setBounds(10, 162, 64, 15);
		lblDescription.setText("Description");
		
		Label lblComments = new Label(shell, SWT.NONE);
		lblComments.setBounds(312, 162, 75, 15);
		lblComments.setText("Comments");
		
		Label bottomSeparator = new Label(shell, SWT.SEPARATOR
				| SWT.HORIZONTAL);
		bottomSeparator.setBounds(10, 399, 604, 2);
		
		setTextFieldValues();
	}
	
	protected abstract void save();
	
	protected abstract void setTextFieldValues();
}
