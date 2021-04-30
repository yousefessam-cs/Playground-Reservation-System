import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Yousef Essam 20180346
 *
 */
public class Player extends Account {
	private eWallet wallet;
	private String location;
	private ArrayList<Booking> bookings = new ArrayList<Booking>();
	private ArrayList<Player> Team = new ArrayList<Player>();

	@Override
	public String toString() {
		return "Player [Username:" + userName + ", email " + email + "]";
	}

	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}

	public ArrayList<Player> getTeam() {
		return Team;
	}

	public void setTeam(ArrayList<Player> team) {
		Team = team;
	}

	Player(String U, String e, String P, String L, eWallet w) {
		super(U, e, P);
		location = L;
		wallet = w;
	}

	public eWallet getWallet() {
		return wallet;
	}

	public void setWallet(eWallet wallet) {
		this.wallet = wallet;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * menu to modify the player's team
	 */
	public void modifyTeam() {
		while (true) {
			try {
				System.out.println("Team members");
				for (int i = 0; i < Team.size(); i++) {
					System.out.println(Team.get(i));
				}
				if (Team.size() == 0) {
					System.out.println("No teammembers yet");
				}
				System.out.println("1-Add a teammate");
				System.out.println("2-Remove a teammate");
				System.out.println("3-Back");
				switch (system.scanner.nextLine()) {
				case "1":
					System.out.println("Enter their email:");
					String sEmail = system.scanner.nextLine();
					if (!system.accounts.contains(new Account("", sEmail, ""))) {
						throw new Exception("Player doesn't exist");
					} else if (Team.contains(new Account("", sEmail, ""))) {
						throw new Exception("teammate already exists");
					} else {
						int index = system.accounts.indexOf(new Account("", sEmail, ""));
						if (system.accounts.get(index) instanceof Player) {
							Team.add((Player) system.accounts.get(index));
							System.out.println("Added Successfully");
						} else {
							throw new Exception("Player doesn't exist");
						}
					}
					break;
				case "2":
					System.out.println("Enter their email:");
					String rEmail = system.scanner.nextLine();
					if (!system.accounts.contains(new Account("", rEmail, ""))) {
						throw new Exception("Player doesn't exist");
					} else if (!Team.contains(new Account("", rEmail, ""))) {
						throw new Exception("teammate doesn't exists");
					} else {
						int index = system.accounts.indexOf(new Account("", rEmail, ""));
						if (system.accounts.get(index) instanceof Player) {
							Team.remove(system.accounts.get(index));
							System.out.println("Removed Successfully");
						} else {
							throw new Exception("Player doesn't exist");
						}
					}
					break;
				case "3":
					break;
				default:
					throw new Exception("Invalid choice");
				}
				break;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * menu to view and cancel booking
	 * <p>
	 * Cancellation isn't required in the final version but it will be kept as a
	 * functionality in the program
	 * </p>
	 */
	public void modifyBookings() {
		while (true) {
			try {
				System.out.println("Your bookings:");
				for (Booking i : bookings) {
					System.out.println(i);
				}
				if (bookings.size() == 0) {
					System.out.println("No bookings yet");
				}
				System.out.println("1-Cancel a booking");
				System.out.println("2-Back");
				switch (system.scanner.nextLine()) {
				case "1":
					System.out.println("Enter booking id:");
					int index = -1;
					int id = system.scanner.nextInt();
					system.scanner.nextLine();
					for (Booking i : bookings) {
						if (i.getBookingID() == id) {
							index = bookings.indexOf(i);
						}
					}
					if (index == -1) {
						throw new Exception("ID doesn't exist");
					}
					Date canceldate = new Date();
					Calendar c = Calendar.getInstance();
					c.setTime(canceldate);
					c.add(Calendar.DATE, bookings.get(index).getPlace().getCancelPeriod());
					canceldate = c.getTime();
					if (canceldate.compareTo(bookings.get(index).getFrom()) >= 0) {
						throw new Exception("Not within cancellation period");
					} else {
						double total = bookings.get(index).getPlace().getPph()
								* (double) bookings.get(index).getDuration();
						wallet.setBalance(total + wallet.getBalance());
						bookings.get(index).getPlace().getBookings().remove(bookings.get(index));
						bookings.remove(index);
						System.out.println("Success!");
					}
					break;
				case "2":
					break;
				default:
					throw new Exception("Invalid choice");
				}
				break;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}
