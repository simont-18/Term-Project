import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;


public class TransferMenu extends JFrame {

	private JPanel contentPane;
	private Account user;
	private JTextField accountNumberTextField;
	private JTextField amountTextField;
	
	public TransferMenu(final Account user) {
		if(user != null)
			this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550,400,500,150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel recipientAccountNumberLabel = new JLabel("Recipient account number:");
		recipientAccountNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		recipientAccountNumberLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(recipientAccountNumberLabel);
		
		accountNumberTextField = new JTextField();
		contentPane.add(accountNumberTextField);
		accountNumberTextField.setColumns(10);
		
		JLabel amountLabel = new JLabel("Amount to be transfer: $");
		amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		amountLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(amountLabel);
		
		amountTextField = new JTextField();
		contentPane.add(amountTextField);
		amountTextField.setColumns(10);
		
		JButton cancleButton = new JButton("Cancle");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                dispose();
			}
		});
		contentPane.add(cancleButton);
		
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
		contentPane.add(doneButton);
	}
}
