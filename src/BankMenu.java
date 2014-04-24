import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class BankMenu extends JFrame {
	private static final long serialVersionUID = 1L;
	//declaring variable
	Account user;
	JTabbedPane tabbedPane;
	JPanel depositP, withdrawP, checkBalanceP, transferP, changePasswordP, exitP;
	JLabel balance;

	public BankMenu(Account user) {
		super("Bank Menu");
		setBounds(400, 400, 650, 300);
		setBackground(Color.gray);

		//define variable user
		this.user = user;
		
		//main pane
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		createDepositP();
		createWithdrawP();
		createCheckBalanceP();
		createTransferP();
		createChangePasswordP();
		createExitP();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Deposit", depositP);
		tabbedPane.addTab("Withdraw", withdrawP);
		tabbedPane.addTab("Check balance", checkBalanceP);
		tabbedPane.addTab("Transfer Money", transferP);
		tabbedPane.addTab("Change Password", changePasswordP);
		tabbedPane.addTab("Exit (Logout)", exitP);

		topPanel.add(tabbedPane, BorderLayout.CENTER);
	}

	private void createDepositP() {
		//define deposit pane
		depositP = new JPanel();
		depositP.setLayout(new GridLayout(2,1));
		
		//declare and define depositTextField to store the amount to be deposit
		final JTextField depositTextField = new JTextField("Enter the amount you wish to deposit");
		depositP.add(depositTextField);
		
		//deposit the amount entered
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String amount = depositTextField.getText();
					user.deposit(Integer.parseInt(amount));
					depositTextField.setText("success");
					balance.setText(String.format("Current balance $:%d",user.getBalance()));
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		depositP.add(depositButton);
	}
	
	private void createWithdrawP() {
		//define withdraw pane
		withdrawP = new JPanel();
		withdrawP.setLayout(new GridLayout(2,1));

		//declare and define withdrawTextField to store the amount to be withdraw
		final JTextField withdrawTextField = new JTextField("Enter the amount you wish to withdraw.");
		withdrawP.add(withdrawTextField);
		
		//withdraw the amount entered
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String amount = withdrawTextField.getText();
					user.withdraw(Integer.parseInt(amount));
					withdrawTextField.setText("success");
					balance.setText(String.format("Current balance $:%d",user.getBalance()));
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		withdrawP.add(withdrawButton);
	}
	
	private void createCheckBalanceP() {
		//define checkBalance pane
		checkBalanceP = new JPanel();
		checkBalanceP.setLayout(new GridLayout(1,1));
		
		//show current balance
		balance = new JLabel();
		balance.setHorizontalAlignment(SwingConstants.CENTER);
		balance.setText(String.format("Current balance $:%d",user.getBalance()));
		checkBalanceP.add(balance);

	}

	private void createTransferP() {
		//define transfer pane
		transferP = new JPanel();
		transferP.setLayout(new GridLayout(3,2));
		
		//define and declare both label and textField to store transfer information
		JLabel recipientAccountNumberLabel = new JLabel("Recipient account number:");
		recipientAccountNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		transferP.add(recipientAccountNumberLabel);
		
		final JTextField accountNumberTextField = new JTextField();
		transferP.add(accountNumberTextField);
		accountNumberTextField.setColumns(10);
		
		JLabel amountLabel = new JLabel("Amount to be transfer: $");
		amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		transferP.add(amountLabel);
		
		final JTextField amountTextField = new JTextField();
		transferP.add(amountTextField);
		amountTextField.setColumns(10);

		transferP.add(new JLabel(""));

		//perform the transfer if everything passes all the checks
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent x) {
				try {
					int accountNumber = Integer.parseInt(accountNumberTextField.getText());
					int amount = Integer.parseInt(amountTextField.getText());
					user.transfer(accountNumber, amount);
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		transferP.add(doneButton);
	}
	
	private void createChangePasswordP() {
		//define changePassword pane
		changePasswordP = new JPanel();
		changePasswordP.setLayout(new GridLayout(4,2));
		
		//define and declare label and passworldField to store information for password change
		final JLabel oldPassLabel = new JLabel("old password:");
		oldPassLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		changePasswordP.add(oldPassLabel);
		
		final JPasswordField oldPassField = new JPasswordField();
		changePasswordP.add(oldPassField);
		
		final JLabel newPassLabel = new JLabel("new password:");
		newPassLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		changePasswordP.add(newPassLabel);
		
		final JPasswordField newPassField = new JPasswordField();
		changePasswordP.add(newPassField);
		
		final JLabel newPassLabel2 = new JLabel("retype new password:");
		newPassLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		changePasswordP.add(newPassLabel2);
		
		final JPasswordField newPassField2 = new JPasswordField();
		changePasswordP.add(newPassField2);
		
		changePasswordP.add(new JLabel(""));
		
		//perform the password change if all the checks passes
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				Scanner in = null;
				PrintWriter out = null;
				try {
					in = new Scanner(new File("Password.txt"));
					String temp = in.next();
					final String password = in.next();
					
					//check to make sure none of the field are empty
					if(oldPassField.getText().equals("") || newPassField.getText().equals("") || newPassField2.getText().equals(""))
						JOptionPane.showMessageDialog(null, "All field must be fill in!", "Error!", JOptionPane.ERROR_MESSAGE);
					else {
						//check to make sure the password is same as the password form Password.txt
						if(oldPassField.getText().equals(password)) {
							//check to make sure old password can not be the same as new password
							if(oldPassField.getText().equals(newPassField.getText())){
								JOptionPane.showMessageDialog(null, "Old password is same as New password!", "Error!", JOptionPane.ERROR_MESSAGE);
							}
							// check to make sure the new password matches and save it to Password.txt
							else if(newPassField.getText().equals(newPassField2.getText())) {
								if(in != null)
									in.close();
								out = new PrintWriter(new File("Password.txt"));
								out.println(temp + " " + newPassField.getText());
								JOptionPane.showMessageDialog(null, "Password have been successfully changed!", "Success!", JOptionPane.INFORMATION_MESSAGE);

							} else 
								JOptionPane.showMessageDialog(null, "New password doesn't match!", "Error!", JOptionPane.ERROR_MESSAGE);
						} else
							JOptionPane.showMessageDialog(null, "Old password is wrong!", "Error!", JOptionPane.ERROR_MESSAGE);
						
					}
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				} finally {
					//closing the input and output stream
					if(in != null)
						in.close();
					if(out != null)
						out.close();
				}
			}
		});
		changePasswordP.add(doneButton);
	}
	
	private void createExitP() {
		//define exit pane
		exitP = new JPanel();
		exitP.setLayout(new GridLayout(1,1));
		
		//exit of ATM when click
		JButton exit = new JButton("Exit (Logout)");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//output to AccountInformation.txt after all transaction have been completed
				PrintWriter out = null;
				try {
					out = new PrintWriter(new File("AccountInformation.txt"));
					out.println(user);
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					//closing stream
					if(out != null)
						out.close();
				}
				System.exit(0);
			}
		});
		exitP.add(exit);
	}
}
