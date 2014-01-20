package tts.presentation;

import java.util.Calendar;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.DateTime;

import acceptanceTests.Register;
import acceptanceTests.EventLoop;

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
	
	private Label lblLoggedInUser;
	private User loggedInUser;
	private List<Task> theTasks;
	private Label lblFilterBy;
	private Label lblTitle;
	private Text txtTitleFilter;
	private Label lblAssignedTo;
	private Combo cboxAssignedToFilter;
	private Label lblStatus;
	private Combo cboxStatusFilter;
	private Label lblDueBetween;
	private Label lblAnd;
	private DateTime dateTimeFromFilter;
	private DateTime dateTimeToFilter;
	private Label lblPriority;
	private Combo cboxPriorityFilter;
	
	private AccessUsers users;
	private AccessTasks tasks;
	private Button btnApplyFilter;
	private Button btnResetFilter;
	
	private String titleFilteredBy;
	private String assignedToFilteredBy;
	private String statusFilteredBy;
	private Calendar fromDateFilteredBy;
	private Calendar toDateFilteredBy;
	private String priorityFilteredBy;
	
	public TaskMainViewWindow(User loggedInUser)
	{
		this.loggedInUser = loggedInUser;
		users = new AccessUsers();
		users.getUsers();
		tasks = new AccessTasks();
		tasks.getTasks();
		Register.newWindow(this);
		createContents();
		open();
	}
	
	/**
	 * Open the window.
	 */
	public void open()
	{
		Display display = Display.getDefault();
		StaticWindowMethods.centerShell(shell, display);
		shell.open();
		shell.layout();
		
		if (EventLoop.isEnabled())
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
		shell.setSize(800, 480);
		shell.setText("TTS - Task Tracker System");
		
		btnSignOut = new Button(shell, SWT.NONE);
		btnSignOut.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				shell.close();
			}
		});
		btnSignOut.setBounds(520, 394, 94, 25);
		btnSignOut.setText("Sign Out");
		
		btnCreateTask = new Button(shell, SWT.NONE);
		btnCreateTask.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				disableMainWindow();
				new CreateTaskWindow();
				enableMainWindow();
				loadTasks();
				filterTasks();
				shell.forceFocus();
				shell.setActive();
			}
		});
		btnCreateTask.setBounds(10, 394, 94, 25);
		btnCreateTask.setText("Create Task");
		
		btnEditTask = new Button(shell, SWT.NONE);
		btnEditTask.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				int row = tblTasks.getSelectionIndex();
				
				if (row >= 0)
				{
					disableMainWindow();
					new EditTaskWindow(tblTasks.getItem(row));
					enableMainWindow();
				}
				else
				{
					MyMessageBox chooseATask = new MyMessageBox(shell,
							"Select a task", "Please select a task to edit.",
							"OK");
					chooseATask.open();
				}
				loadTasks();
				filterTasks();
				shell.forceFocus();
				shell.setActive();
			}
		});
		btnEditTask.setBounds(110, 394, 94, 25);
		btnEditTask.setText("Edit Task");
		
		btnDeleteTask = new Button(shell, SWT.NONE);
		btnDeleteTask.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				deleteSelectedTask();
				shell.forceFocus();
				shell.setActive();
			}
		});
		btnDeleteTask.setBounds(210, 394, 94, 25);
		btnDeleteTask.setText("Delete Task");
		
		tblTasks = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		tblTasks.setBounds(10, 34, 604, 329);
		tblTasks.setHeaderVisible(true);
		tblTasks.setLinesVisible(true);
		
		SelectionAdapter sortColumn = new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				TableColumn colReference = (TableColumn) e.widget;
				
				sort(colReference);
			}
		};
		
		tblclmnTaskID = new TableColumn(tblTasks, SWT.NONE);
		tblclmnTaskID.addSelectionListener(sortColumn);
		tblclmnTaskID.setWidth(100);
		tblclmnTaskID.setText("Task ID");
		
		tblclmnTaskTitle = new TableColumn(tblTasks, SWT.NONE);
		tblclmnTaskTitle.addSelectionListener(sortColumn);
		tblclmnTaskTitle.setWidth(100);
		tblclmnTaskTitle.setText("Task Title");
		
		tblclmnAssignedTo = new TableColumn(tblTasks, SWT.NONE);
		tblclmnAssignedTo.addSelectionListener(sortColumn);
		tblclmnAssignedTo.setWidth(100);
		tblclmnAssignedTo.setText("Assigned To");
		
		tblclmnStatus = new TableColumn(tblTasks, SWT.NONE);
		tblclmnStatus.addSelectionListener(sortColumn);
		tblclmnStatus.setWidth(100);
		tblclmnStatus.setText("Status");
		
		tblclmnDueDate = new TableColumn(tblTasks, SWT.NONE);
		tblclmnDueDate.addSelectionListener(sortColumn);
		tblclmnDueDate.setWidth(100);
		tblclmnDueDate.setText("Due Date");
		
		tblclmnPriority = new TableColumn(tblTasks, SWT.NONE);
		tblclmnPriority.addSelectionListener(sortColumn);
		tblclmnPriority.setWidth(100);
		tblclmnPriority.setText("Priority");
		
		tblclmnDescription = new TableColumn(tblTasks, SWT.NONE);
		tblclmnDescription.addSelectionListener(sortColumn);
		tblclmnDescription.setWidth(100);
		tblclmnDescription.setText("Description");
		
		lblLoggedInUser = new Label(shell, SWT.NONE);
		lblLoggedInUser.setBounds(10, 10, 261, 15);
		lblLoggedInUser
				.setText("Logged In User: " + loggedInUser.getUserName());
		
		loadFilterObjects();
		
		loadTasks();
	}
	
	private void loadFilterObjects()
	{
		List<User> theUsers = users.getUsers();
		
		lblFilterBy = new Label(shell, SWT.NONE);
		lblFilterBy.setBounds(622, 10, 55, 15);
		lblFilterBy.setText("Filter By:");
		
		lblTitle = new Label(shell, SWT.NONE);
		lblTitle.setBounds(622, 34, 55, 15);
		lblTitle.setText("Title:");
		
		txtTitleFilter = new Text(shell, SWT.BORDER);
		txtTitleFilter.setBounds(620, 55, 154, 21);
		
		lblAssignedTo = new Label(shell, SWT.NONE);
		lblAssignedTo.setBounds(622, 82, 74, 15);
		lblAssignedTo.setText("Assigned To:");
		
		cboxAssignedToFilter = new Combo(shell, SWT.READ_ONLY);
		StaticWindowMethods.populateAssignedToDropDown(cboxAssignedToFilter,
				theUsers);
		cboxAssignedToFilter.add(AccessTasks.NO_FILTER, 0);
		cboxAssignedToFilter.setBounds(620, 103, 154, 23);
		
		lblStatus = new Label(shell, SWT.NONE);
		lblStatus.setBounds(622, 132, 55, 15);
		lblStatus.setText("Status:");
		
		cboxStatusFilter = new Combo(shell, SWT.READ_ONLY);
		StaticWindowMethods.populateStatusDropDown(cboxStatusFilter);
		cboxStatusFilter.add(AccessTasks.NO_FILTER, 0);
		cboxStatusFilter.setBounds(620, 153, 154, 23);
		
		lblDueBetween = new Label(shell, SWT.NONE);
		lblDueBetween.setBounds(622, 182, 86, 15);
		lblDueBetween.setText("Due between:");
		
		dateTimeFromFilter = new DateTime(shell, SWT.BORDER);
		dateTimeFromFilter.setBounds(620, 203, 154, 24);
		
		lblAnd = new Label(shell, SWT.NONE);
		lblAnd.setBounds(620, 233, 55, 15);
		lblAnd.setText("and:");
		
		dateTimeToFilter = new DateTime(shell, SWT.BORDER);
		dateTimeToFilter.setBounds(620, 254, 154, 24);
		
		lblPriority = new Label(shell, SWT.NONE);
		lblPriority.setBounds(620, 284, 55, 15);
		lblPriority.setText("Priority");
		
		cboxPriorityFilter = new Combo(shell, SWT.READ_ONLY);
		StaticWindowMethods.populatePriorityDropDown(cboxPriorityFilter);
		cboxPriorityFilter.add(AccessTasks.NO_FILTER, 0);
		cboxPriorityFilter.setBounds(620, 305, 154, 23);
		
		btnApplyFilter = new Button(shell, SWT.NONE);
		btnApplyFilter.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				setFilters();
				filterTasks();
			}
		});
		btnApplyFilter.setBounds(621, 338, 74, 25);
		btnApplyFilter.setText("Apply Filter");
		
		btnResetFilter = new Button(shell, SWT.NONE);
		btnResetFilter.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				loadDefaultFilterOptions();
				loadTasks();
			}
		});
		btnResetFilter.setBounds(699, 338, 75, 25);
		btnResetFilter.setText("Reset Filter");
		loadDefaultFilterOptions();
	}
	
	private void loadDefaultFilterOptions()
	{
		tasks = new AccessTasks();
		tasks.getTasks();
		Calendar minDueDate = Calendar.getInstance();
		minDueDate.set(1800, Calendar.JANUARY, 1);
		
		Calendar maxDueDate = Calendar.getInstance();
		maxDueDate.set(9999, Calendar.DECEMBER, 31);
		
		txtTitleFilter.setText("");
		cboxAssignedToFilter.select(0);
		dateTimeFromFilter.setDate(minDueDate.get(Calendar.YEAR),
				minDueDate.get(Calendar.MONTH),
				minDueDate.get(Calendar.DAY_OF_MONTH));
		dateTimeToFilter.setDate(maxDueDate.get(Calendar.YEAR),
				maxDueDate.get(Calendar.MONTH),
				maxDueDate.get(Calendar.DAY_OF_MONTH));
		cboxStatusFilter.select(0);
		cboxPriorityFilter.select(0);
		setFilters();
	}
	
	private void setFilters()
	{
		titleFilteredBy = txtTitleFilter.getText();
		assignedToFilteredBy = cboxAssignedToFilter.getText();
		statusFilteredBy = cboxStatusFilter.getText();
		fromDateFilteredBy = FormatDate
				.convertDateTimeToCalendar(dateTimeFromFilter);
		toDateFilteredBy = FormatDate
				.convertDateTimeToCalendar(dateTimeToFilter);
		priorityFilteredBy = cboxPriorityFilter.getText();
	}
	
	private void loadTable(List<Task> tasksToLoad)
	{
		if (tasksToLoad != null)
		{
			tblTasks.removeAll();
			for (Task t : tasksToLoad)
			{
				TableItem item = new TableItem(tblTasks, SWT.NONE);
				item.setText(new String[] { "" + t.getTaskID(), t.getTitle(),
						t.getAssignedTo().getUserName(), t.getStatus().name(),
						FormatDate.formatDate(t.getDueDate()).toString(),
						t.getPriority().name(), t.getDescription() });
			}
		}
	}
	
	private void loadTasks()
	{
		if (loggedInUser != null)
		{
			tasks = new AccessTasks();
			
			theTasks = tasks.getTasks();
			
			loadTable(theTasks);
		}
	}
	
	private void filterTasks()
	{
		tasks = new AccessTasks();
		tasks.getTasks();
		List<Task> filteredTasks = tasks.getFilteredTasks(titleFilteredBy,
				assignedToFilteredBy, statusFilteredBy, fromDateFilteredBy,
				toDateFilteredBy, priorityFilteredBy);
		loadTable(filteredTasks);
	}
	
	private void deleteSelectedTask()
	{
		int row = tblTasks.getSelectionIndex();
		int taskID;
		
		if (row >= 0)
		{
			tasks = new AccessTasks();
			tasks.getTasks();
			TableItem item = tblTasks.getItem(row);
			
			try
			{
				taskID = Integer.parseInt(item.getText());
				tasks.deleteTask(taskID);
				tblTasks.remove(row);
			}
			catch (Exception e)
			{
				MyMessageBox deletionFailed = new MyMessageBox(shell, "Error",
						"Deletion of the selected task failed.", "OK");
				deletionFailed.open();
			}
		}
		else
		{
			MyMessageBox chooseATask = new MyMessageBox(shell, "Select a task",
					"Please select a task to delete.", "OK");
			chooseATask.open();
		}
	}
	
	private int getColumnIndex(TableColumn column)
	{
		int columnIndex = -1;
		for (int i = 0; i < tblTasks.getColumnCount(); i++)
		{
			if (tblTasks.getColumn(i) == column)
			{
				columnIndex = i;
				break;
			}
		}
		return columnIndex;
	}
	
	private void disableMainWindow()
	{
		shell.setEnabled(false);
	}
	
	private void enableMainWindow()
	{
		shell.setEnabled(true);
	}
	
	private void sort(TableColumn colReference)
	{
		int columnIndex = getColumnIndex(colReference);
		List<Task> sortedTask;
		tblTasks.setSortColumn(colReference);
		
		if (tblTasks.getSortDirection() == SWT.UP)
		{
			tblTasks.setSortDirection(SWT.DOWN);
		}
		else
		{
			tblTasks.setSortDirection(SWT.UP);
		}
		
		tasks = new AccessTasks();
		sortedTask = tasks.sort(columnIndex,
				tblTasks.getSortDirection() == SWT.DOWN);
		
		if (sortedTask != null)
		{
			tblTasks.removeAll();
			for (Task t : sortedTask)
			{
				initTable(t);
			}
		}
	}
	
	private void initTable(Task t)
	{
		TableItem item = new TableItem(tblTasks, SWT.NONE);
		item.setText(new String[] { "" + t.getTaskID(), t.getTitle(),
				t.getAssignedTo().getUserName(), t.getStatus().name(),
				FormatDate.formatDate(t.getDueDate()).toString(),
				t.getPriority().name(), t.getDescription() });
	}
}
