package tts.presentation;

import java.util.List;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import tts.objects.User;
import tts.business.AccessUsers;

public class LogInWindow
{
	
	private Combo cboxUserDropDown;
	private Button btnLogIn;
	private Button btnQuit;
	private Label lblNewLabel;
	private Label icon;
	
	private AccessUsers users;
	private List<User> theUsers;
	
	private Shell shell;
	private Display display;
	
	public LogInWindow()
	{
		users = new AccessUsers();
		display = Display.getDefault();
		createContents();
		open();
	}
	
	/**
	 * Create contents of the window.
	 */
	public void createContents()
	{
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("TTS - Task Tracker System");
		
		btnLogIn = new Button(shell, SWT.NONE);
		btnLogIn.setBounds(349, 84, 75, 25);
		btnLogIn.setText("Log In");
		btnLogIn.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent arg0)
			{
				if (cboxUserDropDown.getText() != ""
						&& cboxUserDropDown.getSelectionIndex() >= 0)
				{
					String selectedUserName = cboxUserDropDown.getText();
					users.setLoggedInUser(selectedUserName);
					shell.setVisible(false);
					display.sleep();
					new TaskMainViewWindow(AccessUsers.getLoggedInUser());
					display.wake();
					shell.setVisible(true);
					shell.forceFocus();
					shell.setActive();
				}
			}
		});
		
		btnQuit = new Button(shell, SWT.CENTER);
		btnQuit.setBounds(349, 227, 75, 25);
		btnQuit.setText("Quit");
		btnQuit.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent arg0)
			{
				shell.close();
			}
		});
		
		cboxUserDropDown = new Combo(shell, SWT.READ_ONLY);
		cboxUserDropDown.setBounds(146, 86, 195, 23);
		
		initUserDropDown();
		
		lblNewLabel = new Label(shell, SWT.CENTER);
		lblNewLabel.setBounds(197, 21, 183, 25);
		lblNewLabel.setFont(new Font(display, "Times", 14, SWT.BOLD));
		lblNewLabel.setForeground(new Color(display, 200, 0, 0));
		lblNewLabel.setText("Task Tracker System");
		
		icon = new Label(shell, SWT.BORDER);
		icon.setLocation(0, 0);
		icon.setSize(140, 111);
		icon.setImage(new Image(null, "images/task.png"));
		
	}
	
	public void open()
	{
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
	
	protected void initUserDropDown()
	{
		theUsers = users.getUsers();
		
		if (theUsers != null && theUsers.size() != 0)
		{
			for (User u : theUsers)
			{
				cboxUserDropDown.add(u.getUserName());
			}
			cboxUserDropDown.select(0);
		}
		else
		{
			cboxUserDropDown.setEnabled(false);
			btnLogIn.setEnabled(false);
		}
	}
}
