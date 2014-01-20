package tts.presentation;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import acceptanceTests.EventLoop;
import acceptanceTests.Register;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class MyMessageBox extends Dialog {

	protected Object result;
	protected Shell shell;
	private Label contentLabel;
	private Button okButton;
	
	private String messageText;
	private String okButtonText;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public MyMessageBox(Shell parent, String title, String message, String okButton) {
		super(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		setText(title);
		this.messageText = message;
		this.okButtonText = okButton;
		Register.newWindow(this);
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		Display display = getParent().getDisplay();
		createContents();
		StaticWindowMethods.centerShell(shell, display);
		shell.open();
		shell.layout();
		if (EventLoop.isEnabled())
		{
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(320, 145);
		shell.setText(getText());
		
		contentLabel = new Label(shell, SWT.WRAP);
		contentLabel.setBounds(10, 10, 300, 64);
		contentLabel.setText(messageText);
		
		okButton = new Button(shell, SWT.NONE);
		okButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				result = SWT.OK;
				shell.dispose();
			}
		});
		okButton.setSelection(true);
		okButton.setBounds(216, 80, 94, 28);
		okButton.setText(okButtonText);
	}

}
