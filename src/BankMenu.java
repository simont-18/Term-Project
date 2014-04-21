import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
	Account user;
	JTabbedPane tabbedPane;
	JPanel depositP, withdrawP, checkBalanceP, transferP, changePasswordP, logOutP;

	public BankMenu(Account user) {
		super("Bank Menu");
		setBounds(400, 400, 650, 300);
		setBackground(Color.gray);

		this.user = user;
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create the tab pages
		createDepositP();
		createWithdrawP();
		createCheckBalanceP();
		createTransferP();
		createChangePasswordP();
		createLogOutP();

		// Create a tabbed pane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Deposit", depositP);
		tabbedPane.addTab("Withdraw", withdrawP);
		tabbedPane.addTab("Check balance", checkBalanceP);
		tabbedPane.addTab("Transfer Money", transferP);
		tabbedPane.addTab("Change Password", changePasswordP);
		tabbedPane.addTab("LogOut", logOutP);

		topPanel.add( tabbedPane, BorderLayout.CENTER );
	}

	private void createDepositP() {
		// TODO Auto-generated method stub
		depositP = new JPanel();
		depositP.setLayout(new GridLayout(2,1));
		
		final JTextField depositTextField = new JTextField("Enter the amount you wish to deposit");
		depositP.add(depositTextField);
		
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String amount = depositTextField.getText();
					user.deposit(Integer.parseInt(amount));
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		depositP.add(depositButton);
	}
	
	private void createWithdrawP() {
		withdrawP = new JPanel();
		withdrawP.setLayout(new GridLayout(2,1));

		final JTextField withdrawTextField = new JTextField("Enter the amount you wish to withdraw.");
		withdrawP.add(withdrawTextField);
		
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String amount = withdrawTextField.getText();
					user.withdraw(Integer.parseInt(amount));
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		withdrawP.add(withdrawButton);
	}
	
	private void createCheckBalanceP() {
		checkBalanceP = new JPanel();
		checkBalanceP.setLayout(new GridLayout(1,1));
		
		JLabel balance = new JLabel();
		balance.setHorizontalAlignment(SwingConstants.CENTER);
		balance.setText(String.format("Current balance $:%d",user.getBalance()));
		checkBalanceP.add(balance);
	}

	private void createTransferP() {
		transferP = new JPanel();
		transferP.setLayout(new GridLayout(3,2));
		
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

		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent x) {
				try {
					int accountNumber = Integer.parseInt(accountNumberTextField.getText());
					int amount = Integer.parseInt(amountTextField.getText());
					dispose();
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
		changePasswordP = new JPanel();
		changePasswordP.setLayout(new GridLayout(4,2));
		
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
					if(oldPassField.getText().equals("") || newPassField.getText().equals("") || newPassField2.getText().equals(""))
						JOptionPane.showMessageDialog(null, "All field must be fill in!", "Error!", JOptionPane.ERROR_MESSAGE);
					else {
						if(oldPassField.getText().equals(password)) {
							if(oldPassField.getText().equals(newPassField.getText())){
								JOptionPane.showMessageDialog(null, "Old password is aame as New password!", "Error!", JOptionPane.ERROR_MESSAGE);
							} else if(newPassField.getText().equals(newPassField2.getText())) {
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
					dispose();
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				} finally {
					if(in != null)
						in.close();
					if(out != null)
						out.close();
				}
			}
		});
		changePasswordP.add(doneButton);
	}
	
	private void createLogOutP() {
		logOutP = new JPanel();
		logOutP.setLayout(new GridLayout(1,1));
		
		JButton logOut = new JButton("Logout");
		logOutP.add(logOut);
	}
}
