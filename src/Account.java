import java.util.ArrayList;

/**
 * 
 * @author Mahmoud Atya 20180256
 *
 */
public class Account {
	protected String userName;
	protected String email;
	protected String passWord;
	protected ArrayList<String> inbox = new ArrayList<String>();

	Account(String U, String e, String P) {
		userName = U;
		email = e;
		passWord = P;
	}

	public String getUserName() {
		return userName;
	}

	public String getEmail() {
		return email;
	}

	public ArrayList<String> getInbox() {
		return inbox;
	}

	public void setInbox(ArrayList<String> inbox) {
		this.inbox = inbox;
	}

	/**
	 * checks the email and password against <code>this</code>'s email and password
	 * 
	 * @param U email to check
	 * @param P password to check
	 * @return true if succesful, false otherwise
	 */
	public boolean login(String U, String P) {
		return (U.equalsIgnoreCase(email) && P.equals(passWord));
	}

	public void inboxToString() {
		for (String e : inbox) {
			System.out.println(e);
		}
	}

	/**
	 * @return true if the emails are equal, false if the emails are not equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Account other = (Account) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
}
