import javax.swing.JOptionPane;

public class Account {
	//declaring variable to store Account attribute
	private int accountNumber, balance;
	private String lastName, firstName;
	private boolean atmAccessible;
	
	Account(int accountNumber, String lastName, String firstName, int balance, boolean atmAccessible) {
		//constructor defining Account attribute from the constructor parameter
		this.accountNumber = accountNumber;
		this.lastName = lastName;
		this.firstName = firstName;
		this.balance = balance;
		this.atmAccessible = atmAccessible;
	}

	//getter functions for all attribute
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
	
	public String isAtmAccessibleString() {
		if(isAtmAccessible())
			return "Active";
		else
			return "Disable";
	}
	
	public void withdraw(int amount) {
		//check to make sure the amount is positive and there is sufficient fund in the account
		if(amount < 0)
			JOptionPane.showMessageDialog(null, "Negative amount entered.", "Error!", JOptionPane.ERROR_MESSAGE);
		else if(balance - amount < 0)
			JOptionPane.showMessageDialog(null, "Insufficient amount in your account!", "Warning", JOptionPane.WARNING_MESSAGE);
		else 
			balance -= amount;
	}
	
	public void deposit(int amount) {
		//check to make sure the deposit amount is a positive amount
		if(amount < 0)
			JOptionPane.showMessageDialog(null, "Negative amount entered.", "Error!", JOptionPane.ERROR_MESSAGE);
		else 
			balance += amount;
	}

	public void transfer(int transferAccountNumber, int amount) {
		//make sure the transfer amount is positive and there is sufficient fund in the account
		if(amount < 0)
			JOptionPane.showMessageDialog(null, "Negative amount entered.", "Error!", JOptionPane.ERROR_MESSAGE);
		else if(balance - amount < 0)
			JOptionPane.showMessageDialog(null, "Insufficient amount in your account!", "Warning", JOptionPane.WARNING_MESSAGE);
		else {
			balance -= amount;
			JOptionPane.showMessageDialog(null, "You have successfully transfered $" + amount + " to this account number: " + transferAccountNumber + '.', "Success!", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	public String toString() {
		return String.format("%d\n%s\n%s\n%d\n%s", getAccountNumber(), getLastName(), getFirstName(), getBalance(), isAtmAccessibleString());
	}
}