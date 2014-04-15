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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;


public class PasswordChange extends JFrame {

	private JPanel contentPane;
	private JPasswordField oldPassField;
	private JPasswordField newPassField;
	private JPasswordField newPassField2;
	private Account user;

	public PasswordChange(final Account user) {
		this.user = user;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600,300,400,360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		final JLabel oldPassLabel = new JLabel("old password:");
		oldPassLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		oldPassLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(oldPassLabel);
		
		oldPassField = new JPasswordField();
		contentPane.add(oldPassField);
		
		final JLabel newPassLabel = new JLabel("new password:");
		newPassLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		newPassLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(newPassLabel);
		
		newPassField = new JPasswordField();
		contentPane.add(newPassField);
		
		final JLabel newPassLabel2 = new JLabel("retype new password:");
		newPassLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		newPassLabel2.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(newPassLabel2);
		
		newPassField2 = new JPasswordField();
		contentPane.add(newPassField2);
		
		JButton cancleButton = new JButton("Cancle");
		cancleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                dispose();
			}
		});
		cancleButton.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(cancleButton);
		
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
		doneButton.setFont(new Font("Arial", Font.PLAIN, 20));
		contentPane.add(doneButton);
	}
}
