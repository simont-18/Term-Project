import java.awt.Font;
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
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class ATM extends JFrame {
 static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField accountNumberTextField;
	private JPasswordField passwordField;
	private int attempts = 0;
	private Scanner in = null;
	private Account user = null;
	
	public ATM() {
		super("ATM Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 650, 300);
		//setSize(600,400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 0));
		
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
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(logInCheck()) {
						getAccountInfo();
						if(user.isAtmAccessible()) {
							BankMenu frame = new BankMenu(user);
							frame.setVisible(true);
							frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		in = new Scanner(new File("Password.txt"));
		int accountNumber = 0;
		String password = null;
		
		if(in.hasNext()) {
			accountNumber = Integer.parseInt((String) in.next());
			if(in.hasNext())
				password = (String) in.next();
		}
		
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
		in = new Scanner(new File("AccountInformation.txt"));
		
		int accountNumber = Integer.parseInt((String) in.nextLine());
		String lastName = (String) in.nextLine();
		String firstName = (String) in.nextLine();
		int balance = Integer.parseInt((String) in.nextLine());
		
		boolean accessible = false;
		String temp = in.nextLine();
		if((temp.toLowerCase()).equals("active"))
			accessible = true;
		else if((temp.toLowerCase()).equals("disable"))
			accessible = false;
		else
			accessible = false;
		user = new Account(accountNumber, lastName, firstName, balance, accessible);
		in.close();
	}
}
