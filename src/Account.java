import javax.swing.JOptionPane;


public class Account {
	private int accountNumber, balance;
	private String lastName, firstName;
	private boolean atmAccessible;
	
	Account(int accountNumber, String lastName, String firstName, int balance, boolean atmAccessible) {
		this.accountNumber = accountNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.balance = balance;
		this.atmAccessible = atmAccessible;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public int getBalance() {
		return balance;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public boolean isAtmAccessible() {
		return atmAccessible;
	}
	
	public void withdraw(int amount) {
		if(amount < 0)
			JOptionPane.showMessageDialog(null, "Negative amount entered.", "Error!", JOptionPane.ERROR_MESSAGE);
		else if(balance - amount < 0)
			JOptionPane.showMessageDialog(null, "Insufficient amount in your account!", "Warning", JOptionPane.WARNING_MESSAGE);
		else 
			balance -= amount;
	}
	
	public void deposit(int amount) {
		if(amount < 0)
			JOptionPane.showMessageDialog(null, "Negative amount entered.", "Error!", JOptionPane.ERROR_MESSAGE);
		else 
			balance += amount;
	}

	public void transfer(int accountNumber2, int amount) {
		if(amount < 0)
			JOptionPane.showMessageDialog(null, "Negative amount entered.", "Error!", JOptionPane.ERROR_MESSAGE);
		else {
			balance -= amount;
			JOptionPane.showMessageDialog(null, "You have successfully transfered $" + amount + " to this account number: " + accountNumber2 + '.', "Success!", JOptionPane.PLAIN_MESSAGE);
		}
	}
}