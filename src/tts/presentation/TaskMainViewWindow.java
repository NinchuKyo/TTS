package tts.presentation;

import java.util.List;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Label;

import tts.business.*;
import tts.objects.*;

public class TaskMainViewWindow
{
	
	protected Shell shell;
	
	private Button btnSignOut;
	private Button btnCreateTask;
	private Button btnEditTask;
	private Button btnDeleteTask;
	
	private Table tblTasks;
	private TableColumn tblclmnTaskID;
	private TableColumn tblclmnTaskTitle;
	private TableColumn tblclmnAssignedTo;
	private TableColumn tblclmnStatus;
	private TableColumn tblclmnDueDate;
	private TableColumn tblclmnPriority;
	private TableColumn tblclmnDescription;
	
	private Label lblNewLabel;
	private Label lblLoggedInUser;
	
	private User loggedInUser;
	private List<Task> theTasks;
	
	public TaskMainViewWindow(User loggedInUser)
	{
		this.loggedInUser = loggedInUser;
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed())
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
		shell = new Shell();
		shell.setSize(740, 426);
		shell.setText("TTS - Task Tracker System");
		
		btnSignOut = new Button(shell, SWT.NONE);
		btnSignOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnSignOut.setBounds(620, 339, 94, 25);
		btnSignOut.setText("Sign Out");
		
		btnCreateTask = new Button(shell, SWT.NONE);
		btnCreateTask.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				disableMainWindow();
				new CreateTaskWindow();
				enableMainWindow();
				loadTasks();
				shell.forceFocus();
				shell.setActive();
			}
		});
		btnCreateTask.setBounds(620, 180, 94, 25);
		btnCreateTask.setText("Create Task");
		
		btnEditTask = new Button(shell, SWT.NONE);
		btnEditTask.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int row = tblTasks.getSelectionIndex();
				
				if(row >= 0)
				{
					disableMainWindow();
					new EditTaskWindow(tblTasks.getItem(row));
					enableMainWindow();
				}
				loadTasks();
				shell.forceFocus();
				shell.setActive();
			}
		});
		btnEditTask.setBounds(620, 232, 94, 25);
		btnEditTask.setText("Edit Task");
		
		btnDeleteTask = new Button(shell, SWT.NONE);
		btnDeleteTask.setBounds(620, 285, 94, 25);
		btnDeleteTask.setText("Delete Task");
		
		tblTasks = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		tblTasks.setBounds(10, 34, 604, 329);
		tblTasks.setHeaderVisible(true);
		tblTasks.setLinesVisible(true);
		
		tblclmnTaskID = new TableColumn(tblTasks, SWT.NONE);
		tblclmnTaskID.setWidth(100);
		tblclmnTaskID.setText("Task ID");
		
		tblclmnTaskTitle = new TableColumn(tblTasks, SWT.NONE);
		tblclmnTaskTitle.setWidth(100);
		tblclmnTaskTitle.setText("Task Title");
		
		tblclmnAssignedTo = new TableColumn(tblTasks, SWT.NONE);
		tblclmnAssignedTo.setWidth(100);
		tblclmnAssignedTo.setText("Assigned To");
		
		tblclmnStatus = new TableColumn(tblTasks, SWT.NONE);
		tblclmnStatus.setWidth(100);
		tblclmnStatus.setText("Status");
		
		tblclmnDueDate = new TableColumn(tblTasks, SWT.NONE);
		tblclmnDueDate.setWidth(100);
		tblclmnDueDate.setText("Due Date");
		
		tblclmnPriority = new TableColumn(tblTasks, SWT.NONE);
		tblclmnPriority.setWidth(100);
		tblclmnPriority.setText("Priority");
		
		tblclmnDescription = new TableColumn(tblTasks, SWT.NONE);
		tblclmnDescription.setWidth(100);
		tblclmnDescription.setText("Description");
				
		lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBounds(620, 34, 94, 125);
		lblNewLabel.setText("An image perhaps?");
		
		lblLoggedInUser = new Label(shell, SWT.NONE);
		lblLoggedInUser.setBounds(10, 10, 261, 15);
		lblLoggedInUser.setText("Logged In User: " + loggedInUser.getUserName());
		
		loadTasks();
	}
	
	protected void loadTasks()
	{
		if(loggedInUser != null)
		{
			AccessTasks tasks = new AccessTasks();
			
			theTasks = tasks.getTasks();
			
			if(theTasks != null)
			{
				tblTasks.removeAll();
				for(Task t : theTasks)
				{
					TableItem item = new TableItem(tblTasks, SWT.NONE);
					item.setText(new String[] {""+t.getTaskID(), t.getTitle(), t.getAssignedTo().getUserName(), t.getStatus().name(), t.getDueDate().toString(), t.getPriority().name(), t.getDescription()});
				}
			}
		}
	}
	
	protected void disableMainWindow()
	{
		shell.setEnabled(false);
	}
	
	protected void enableMainWindow()
	{
		shell.setEnabled(true);
	}
}
