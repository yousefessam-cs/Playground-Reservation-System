/**
 * 
 * @author Yousef Essam 20180346
 *
 */
public class eWallet {
	private double balance = 1000;
	private String password;
	private String Number;

	public String getPassword() {
		return password;
	}

	public String getNumber() {
		return Number;
	}

	public eWallet(String password, String number) throws Exception {
		try {
			Integer.parseUnsignedInt(number);
		} catch (NumberFormatException nfe) {
			throw new Exception("invalid eWallet number");
		}
		this.password = password;
		Number = number;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double b) {
		balance = b;
	}

}
