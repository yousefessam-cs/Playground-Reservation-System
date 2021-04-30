/**
 * 
 * @author Mahmoud Atya 20180256
 *
 */
public class Administrator extends Account {
	Administrator(String U, String e, String P) {
		super(U, e, P);
	}

	@Override
	public String toString() {
		return "Administrator [userName=" + userName + ", email=" + email + "]";
	}

	/**
	 * approves playground
	 * <p>
	 * changes playground state to AVALIABLE
	 * </p>
	 * 
	 * @param toApprove the playground chosen to be approved
	 */
	public void approve(Playground toApprove) {
		toApprove.setPlaygroundState(State.AVALIABLE);
	}

}
