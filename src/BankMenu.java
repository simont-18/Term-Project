import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class BankMenu extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Account user;

	public BankMenu(Account a) {
		if(a != null)
			this.user = a;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600,300,400,360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton depositButton = new JButton("Deposit");
		depositButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String amount = JOptionPane.showInputDialog("Enter the amount to be deposit.");
					user.deposit(Integer.parseInt(amount));
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(depositButton);
		
		JButton withdrawButton = new JButton("Withdraw");
		withdrawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String amount = JOptionPane.showInputDialog("Enter the amount to be withdraw.");
					user.withdraw(Integer.parseInt(amount));
				} catch(Exception e) {
					if(!e.getMessage().equals("null"))
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(withdrawButton);
		
		JButton balanceButton = new JButton("Check Balance");
		balanceButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Current balance: $" + user.getBalance(), "Bank Balance", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		contentPane.add(balanceButton);
		
		JButton transferButton = new JButton("Make a transfer");
		transferButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					TransferMenu transfer = new TransferMenu(user);
					transfer.setVisible(true);
					transfer.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(transferButton);
		
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					PasswordChange change = new PasswordChange(user);
					change.setVisible(true);
					change.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				} catch(Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		contentPane.add(changePasswordButton);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		contentPane.add(logoutButton);
	}

}
