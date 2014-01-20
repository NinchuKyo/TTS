package tts.presentation;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;

import tts.business.AccessUsers;
import tts.objects.User;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import acceptanceTests.Register;
import acceptanceTests.EventLoop;

public class CreateUserWindow
{
	
	protected Shell shell;
	private Text txtUserName;
	private AccessUsers userAccess;
	private Button btnCreate;
	private Button btnCancel;
	
	public CreateUserWindow(AccessUsers userAccess)
	{
		this.userAccess = userAccess;
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
		createContents();
		StaticWindowMethods.centerShell(shell, display);
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
		shell.setSize(450, 80);
		shell.setText("Create New User");
		
		txtUserName = new Text(shell, SWT.BORDER);
		txtUserName.setTextLimit(User.MAX_NAME_LENGTH);
		txtUserName.setBounds(116, 7, 146, 21);
		
		Label lblEnterUserName = new Label(shell, SWT.NONE);
		lblEnterUserName.setBounds(10, 10, 100, 15);
		lblEnterUserName.setText("Enter User Name");
		
		btnCreate = new Button(shell, SWT.NONE);
		btnCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				User user;
				try
				{
					user = new User(txtUserName.getText());
					userAccess.getUsers();
					if (userAccess.addUser(user) == false)
					{
						MyMessageBox error = new MyMessageBox(shell, "Username already exists", "Please create a unique username.", "OK");
						error.open();
					}
					else
					{
						MyMessageBox success = new MyMessageBox(shell, "User created", "User has been created.", "OK");
						success.open();
						shell.close();
					}
				}
				catch (IllegalArgumentException iae)
				{
					MyMessageBox error = new MyMessageBox(shell, "Illegal username", "Make sure your username only contains alphanumeric characters.", "OK");
					error.open();
				}
			}
		});
		btnCreate.setBounds(268, 5, 75, 25);
		btnCreate.setText("Create");
		
		btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				shell.close();
			}
		});
		btnCancel.setBounds(349, 5, 75, 25);
		btnCancel.setText("Cancel");
		
	}
}
