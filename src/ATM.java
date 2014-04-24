import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ATM extends JFrame {
	//declare ATM attribute
	static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField accountNumberTextField;
	private JPasswordField passwordField;
	private int attempts = 0;
	private Scanner in = null;
	private Account user;
	
	public ATM() {
		super("ATM Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 650, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0));
		
		//declare and defining labels and textFields
		JLabel accountNumberLabel = new JLabel("Account Number:");
		accountNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(accountNumberLabel);
		
		accountNumberTextField = new JTextField();
		contentPane.add(accountNumberTextField);
		accountNumberTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		//Log user into the bank account if accountNumber and password are correct
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					//logInCheck() is use to verify login information
					if(logInCheck()) {
						//retrieve the user information from AccountInformation.txt and define Account user 
						getAccountInfo();
						
						//check to make sure the user have ATM access
						if(user.isAtmAccessible()) {
							//create the BankMenu frame
							BankMenu frame = new BankMenu(user);
							frame.setVisible(true);
							frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							
							//clear out ATM frame
							dispose();
						} else						
							JOptionPane.showMessageDialog(null, "Sorry you do not have ATM access.", "Not accessible!", JOptionPane.WARNING_MESSAGE);						
					}
					else 
						JOptionPane.showMessageDialog(null, "You have enter the wrong account number and/or password!", "Error!", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JLabel label = new JLabel("");
		contentPane.add(label);
		contentPane.add(LoginButton);
	}
	
	public boolean logInCheck() throws IOException, NumberFormatException {
		//define scanner to use to read data from Password.txt
		in = new Scanner(new File("Password.txt"));
		
		//declare variable to store information read from Password.txt
		int accountNumber = 0;
		String password = null;
		
		//read in accountNumber
		if(in.hasNext()) {
			accountNumber = Integer.parseInt((String) in.next());
			//read in password
			if(in.hasNext())
				password = (String) in.next();
		}
		
		//check to make sure there is still remaining attempts and check both accountNumber and password
		//otherwise, increase attempt accordingly
		if(attempts < 3 && password.equals(passwordField.getText()) == true && accountNumber == Integer.parseInt(accountNumberTextField.getText())) {
			in.close();
			return true;
		}
		else {
			if(attempts >= 3) {
				JOptionPane.showMessageDialog(null, "You have exceed the three attempts limit, the program will now exit!", "Error!", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
			attempts++;
			in.close();
			return false;
		}	
	}
	
	public void getAccountInfo() throws FileNotFoundException, NumberFormatException {
		//define scanner to use to read data from AccountInformation.txt
		in = new Scanner(new File("AccountInformation.txt"));
		
		//read in and store them in different variable
		int accountNumber = Integer.parseInt((String) in.nextLine());
		String lastName = (String) in.nextLine();
		String firstName = (String) in.nextLine();
		int balance = Integer.parseInt((String) in.nextLine());
		
		boolean accessible = false;
		
		//user String temp to store ATM access status and define it to accessible accordingly
		String temp = in.nextLine();
		if((temp.toLowerCase()).equals("active"))
			accessible = true;
		else if((temp.toLowerCase()).equals("disable"))
			accessible = false;
		else
			accessible = false;
		
		//define user
		user = new Account(accountNumber, lastName, firstName, balance, accessible);
		in.close();
	}
}
