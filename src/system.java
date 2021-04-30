import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * <strong>Main class</strong>
 * 
 * @author Ahmed Rushdi Elkilani 20180008 ahmedghjkhk@gmail.com
 *
 */
public class system {
	/**
	 * used to get input from the console
	 */
	public static Scanner scanner = new Scanner(System.in);
	/**
	 * used to store accounts
	 */
	public static ArrayList<Account> accounts = new ArrayList<Account>();
	/**
	 * used to store playgrounds
	 */
	public static ArrayList<Playground> playgrounds = new ArrayList<Playground>();
	/**
	 * used to determine when to exit the program
	 */
	public static boolean running = true;
	/**
	 * used to save the current index of the logged in account
	 */
	public static int currentIndex = -1;

	/**
	 * generates verifiaction code
	 * 
	 * @return Code a randomly generated string of length 6
	 */
	public static String rand() {
		String charsNums = "0123456789" + "abcdefghijklmnopqrstuvxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		StringBuilder code = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			int index = (int) (charsNums.length() * Math.random());

			code.append(charsNums.charAt(index));
		}

		return code.toString();
	}

	/**
	 * main function
	 * <p>
	 * Contains the first menu<br>
	 * Signup, Signin, Exit
	 * </p>
	 * 
	 * @param args args
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to GoFo playground reservation");
		while (running) {
			while (currentIndex == -1 && running) {
				try {
					System.out.println("1-Register a new account");
					System.out.println("2-Login to your account");
					System.out.println("3-Exit");
					switch (scanner.nextLine()) {
					case "1":
						registerMenu();
						break;
					case "2":
						loginMenu();
						break;
					case "3":
						running = false;
						break;
//					case "4":
//						for (Account i : accounts) {
//							System.out.println(i);
//						}
//						break;
//					case "5":
//						for (Playground i : playgrounds) {
//							System.out.println(i);
//						}
//						break;
					default:
						throw new Exception("Invalid choice");
					}
				} catch (Exception e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
			while (currentIndex != -1) {
				try {
					if (accounts.get(currentIndex) instanceof Player) {
						PlayerMenu();
					} else if (accounts.get(currentIndex) instanceof PlaygroundOwner) {
						OwnerMenu();
					} else if (accounts.get(currentIndex) instanceof Administrator) {
						adminMenu();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * contains the add a playground menu
	 * <p>
	 * Takes input using scanner and uses it to intialize an instance of
	 * <code>Playground</code>. Adds the instance to the <code>playgrounds</code>
	 * ArrayList
	 * </p>
	 * <p>
	 * Accessbile for <code>PlaygroundOwner</code> only
	 * </p>
	 */
	public static void addPlayground() {

		System.out.println("Enter playground name:");
		String n = scanner.nextLine();
		System.out.println("Enter playground address following this format (Street-region-city)");
		String a = scanner.nextLine();
		System.out.println("Enter Pay Per Hour (PPH)");
		double pph = scanner.nextDouble();
		while (true) {

			try {
				Playground temp = new Playground(n, a, pph);
				((PlaygroundOwner) accounts.get(currentIndex)).addPlayground(temp);
				playgrounds.add(temp);
				break;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(" \n to cancel type c or any other input to retry");
				if (scanner.nextLine().equalsIgnoreCase("c"))
					break;
			}
		}
	}

	/**
	 * contains the registeration enu
	 * <p>
	 * Takes input using scanner and uses it to intialize an instance of
	 * <code>Account</code>'s Childern classes. Adds the instance to the
	 * <code>accounts</code> ArrayList
	 * </p>
	 * 
	 * @throws Exception - Form validation Errors
	 */
	public static void registerMenu() throws Exception {
		String UN, EM, PW;
		System.out.println("Register");
		System.out.println("________");
		System.out.println("Username");
		UN = scanner.nextLine();
		String regexName = "^[a-zA-Z0-9_@-]{4,}$";
		if (!Pattern.matches(regexName, UN)) {
			throw new Exception(
					"Username should be at least 4 chars in length, alphanumerical and can contain these special characters (_@-)");
		}

		System.out.println("Email");
		EM = scanner.nextLine();
		String regexEmail = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
		if (!Pattern.matches(regexEmail, EM)) {
			throw new Exception(
					"Email should follow this fromat( alphanumerical and (_.-) + @ + alphanumerical and (-) + . + alphanumerical and (.)");
		}

		System.out.println("Password");
		PW = scanner.nextLine();
		String regexPassword = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z]).{8,}$";
		if (!Pattern.matches(regexPassword, PW)) {
			throw new Exception(
					"Password should be at least 8 chars in length and has atleast 1 Uppercase, 1 Lowercase, 1 Number");
		}
		while (true) {
			String verifCode = rand();
			System.out.println("Enter Verifaction Code:" + verifCode);
			String x = scanner.nextLine();
			if (x.equals(verifCode)) {
				break;
			}
			System.out.println("Invalid Verification code, Please Retry!");
		}
		Account temp = new Account(UN, EM, PW);
		if (accounts.contains(temp)) {
			throw new Exception("email is already registered");
		}

		boolean loop = true;
		int choice = 0;
		while (loop) {
			try {
				System.out.println("Choose Account type:");
				System.out.println("1-Player");
				System.out.println("2-Playground Owner");
				System.out.println("3-Administrator");
				System.out.println("4-Back");
				choice = scanner.nextInt();
				scanner.nextLine();
				switch (choice) {
				case 1:
					System.out.println("Enter your Street,Neighbourhood and City separated by a '-':");
					String L = scanner.nextLine();
					if (L.split("-").length != 3) {
						throw new Exception("Wrong location fromat");
					}
					System.out.println("Enter eWallet number");
					String num = scanner.nextLine();
					System.out.println("Enter eWallet password");
					String walletPW = scanner.nextLine();
					eWallet w = new eWallet(walletPW, num);
					temp = new Player(UN, EM, PW, L, w);
					loop = false;
					break;
				case 2:
					System.out.println("Enter Address:");
					String A = scanner.nextLine();
					System.out.println("Enter Phonenumber:");
					String PN = scanner.nextLine();
					Long.parseUnsignedLong(PN);
					System.out.println("Enter eWallet number");
					String num1 = scanner.nextLine();
					System.out.println("Enter eWallet password");
					String walletPW1 = scanner.nextLine();
					eWallet w1 = new eWallet(walletPW1, num1);
					temp = new PlaygroundOwner(UN, EM, PW, A, PN, w1);
					loop = false;
					break;
				case 3:
					temp = new Administrator(UN, EM, PW);
					loop = false;
					break;
				case 4:
					loop = false;
					break;
				default:
					System.out.println("wrong choice, retry");
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
			}

		}
		if (choice != 4)
			accounts.add(temp);

	}

	/**
	 * contains the login menu
	 * <p>
	 * Takes input using scanner and uses it to search the accounts arraylist for
	 * the correct combination
	 * </p>
	 * <p>
	 * If the right combination is found its index is saved in currentIndex
	 * varaible. Else the user can either retry or return to main.
	 * </p>
	 */
	public static void loginMenu() {
		String U, P;
		System.out.println("Login");
		System.out.println("_____");
		System.out.println("Enter Email");
		U = scanner.nextLine();

		System.out.println("Enter Password");
		P = scanner.nextLine();
		int i;
		for (i = 0; i < accounts.size(); i++) {
			if (accounts.get(i).login(U, P)) {
				currentIndex = i;
				break;
			}
		}
		if (i == accounts.size()) {
			System.out.println("Invalid login info, please retry!");
			System.out.println("e to Main menu or enter to retry");
			if (!scanner.nextLine().equals("e")) {
				loginMenu();
			}
		}
	}

	/**
	 * Simply sets the currentIndex varaible to -1
	 */
	public static void logout() {
		currentIndex = -1;
	}

	/**
	 * contains the full booking process
	 * <p>
	 * Accessbile for <code>Player</code> only
	 * </p>
	 * 
	 * @param toBook - the playground chosen to be booked
	 * @param date   - the day in which to book
	 */
	public static void bookPlayground(Playground toBook, String date) {
		String from, to;
		int choice = 0;
		ArrayList<Player> toInvite = new ArrayList<Player>();

		boolean loop = true;
		while (loop) {
			System.out.println("Choose Who to invite");
			System.out.println("1-Enter individual name and email");
			System.out.println("2-Your Team");
			System.out.println("3-Proceed");
			System.out.println("4-Remove a player from the List");
			System.out.println("5-Back");
			choice = scanner.nextInt();
			system.scanner.nextLine();
			switch (choice) {
			case 1:
				System.out.println("Enter Username");
				String name = scanner.nextLine();
				System.out.println("Enter email");
				String email = scanner.nextLine();
				boolean found = false;
				int i;
				for (i = 0; i < accounts.size(); i++) {
					if (accounts.get(i).getUserName().equalsIgnoreCase(name)
							&& accounts.get(i).getEmail().equalsIgnoreCase(email)
							&& accounts.get(i) instanceof Player) {
						found = true;
						if (toInvite.contains(accounts.get(i))) {
							System.out.println("Player already added");
						} else {
							toInvite.add((Player) accounts.get(i));
						}
					}
				}
				if (found == false) {
					System.out.println("Player not found in the system");
				}
				break;
			case 2:
				for (int j = 0; j < ((Player) accounts.get(currentIndex)).getTeam().size(); j++) {
					if (!(toInvite.contains(((Player) accounts.get(currentIndex)).getTeam().get(j)))) {
						toInvite.add(((Player) accounts.get(currentIndex)).getTeam().get(j));
					}
				}

				break;
			case 3:
				if (!(toInvite.contains(accounts.get(currentIndex)))) {
					toInvite.add((Player) accounts.get(currentIndex));
				}
				loop = false;
				break;
			case 4:
				if (toInvite.size() == 0) {
					System.out.println("There are no players in the List");
				} else {
					for (int k = 0; k < toInvite.size(); k++) {
						System.out.println((k + 1) + "-" + toInvite.get(k));
					}
					int toRemove = scanner.nextInt();
					scanner.nextLine();
					if (toRemove >= toInvite.size() && toRemove <= 0) {
						System.out.println("wrong choice");
					} else {
						toInvite.remove(toRemove - 1);
					}
				}
				break;
			case 5:
				break;
			default:
				System.out.println("Wrong choice!");
			}
		}
		if (choice != 5) {

			try {
				System.out.println("Please enter starting hour (24-hour format)");
				from = scanner.nextLine();
				System.out.println("Please enter ending hour (24-hour format)");
				to = scanner.nextLine();
				double total = (double) (Integer.parseInt(to) - Integer.parseInt(from)) * toBook.getPph();
				if ((Integer.parseInt(from) < toBook.getAvaliableTime().from
						|| Integer.parseInt(from) > toBook.getAvaliableTime().to)
						|| (Integer.parseInt(to) < toBook.getAvaliableTime().from
								|| Integer.parseInt(to) > toBook.getAvaliableTime().to)) {
					throw new Exception("Hours not avaliable");
				}
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH");
				String fromDate = date + " " + from;
				String toDate = date + " " + to;
				Booking temp = new Booking(formatter.parse(fromDate), formatter.parse(toDate), toBook, toInvite);

				for (int i = 0; i < toBook.getBookings().size(); i++) {
					if (toBook.getBookings().get(i).collides(temp)) {
						throw new Exception("The hours entred collide with other bookings in the playground");
					}
				}
				System.out.println("Total :" + total + " EGP");
				System.out.println("Enter eWallet number");
				String n = scanner.nextLine();
				System.out.println("Enter eWallet password");
				String pass = scanner.nextLine();
				if (n.equals(((Player) accounts.get(currentIndex)).getWallet().getNumber())
						&& pass.equals(((Player) accounts.get(currentIndex)).getWallet().getPassword())) {
					System.out.println("New balance will be "
							+ (((Player) accounts.get(currentIndex)).getWallet().getBalance() - total) + " EGP");
					System.out.println("Confirm?(Y/N will take you back to main menu)");
					String choice2 = scanner.nextLine();
					if ((choice2.equalsIgnoreCase("y"))
							&& ((Player) accounts.get(currentIndex)).getWallet().getBalance() >= total) {
						((Player) accounts.get(currentIndex)).getWallet()
								.setBalance(((Player) accounts.get(currentIndex)).getWallet().getBalance() - total);
						toBook.getOwner().getWallet().setBalance(toBook.getOwner().getWallet().getBalance() + total);
						temp.invitePlayers();
						toBook.getBookings().add(temp);
						((Player) accounts.get(currentIndex)).getBookings().add(temp);
					} else if ((choice2.equalsIgnoreCase("y"))
							&& ((Player) accounts.get(currentIndex)).getWallet().getBalance() < total) {
						System.out.println("insufficient balance");
					}
				} else {
					throw new Exception("invalid eWallet information");
				}
			} catch (Exception e) {
				System.out.println("incorrect date entered" + e);
			}
		}
	}

	/**
	 * contains the approval menu
	 * <p>
	 * Accessbile for Administrator only
	 * </p>
	 */
	public static void approveMenu() {

		ArrayList<Playground> waiting = new ArrayList<Playground>(playgrounds);
		for (int i = waiting.size() - 1; i >= 0; i--) {

			if (waiting.get(i).getPlaygroundState() != State.WAITING) {
				waiting.remove(waiting.get(i));
			}
		}
		if (waiting.size() > 0) {
			System.out.println("Here is a list of waiting playgrounds");
			int i = 1;
			for (Playground temp : waiting) {
				System.out.println(i + "-" + temp);
				i++;
			}
			System.out.println("Choose Playground to approve");
			i = scanner.nextInt();
			scanner.nextLine();
			((Administrator) accounts.get(currentIndex)).approve(waiting.get(i - 1));

		} else {
			System.out.println("no waiting playgrounds");
		}
	}

	/**
	 * contains the filter and sort menu
	 */
	public static void viewFilter() {
		System.out.println("How do you want to display the playgrounds");
		System.out.println("1-Sort by Location");
		System.out.println("2-Sort by Price per hour");
		System.out.println("3-Filter by Avaliable time");
		System.out.println("Press anything else to go back");
		int choice = 0;
		String date;

		ArrayList<Playground> avaliable = new ArrayList<Playground>(playgrounds);
		switch (scanner.nextInt()) {
		case 1:
			scanner.nextLine();
			Collections.sort(avaliable, new locationComparator());
			for (int i = avaliable.size() - 1; i >= 0; i--) {
				if (avaliable.get(i).getPlaygroundState() != State.AVALIABLE) {
					avaliable.remove(i);
				}
			}

			while (true) {
				try {
					for (int i = 0; i < avaliable.size(); i++) {
						System.out.println((i + 1) + "-" + avaliable.get(i));
					}
					if (avaliable.size() == 0) {
						System.out.println("no Playgrounds avaliable");
						break;
					}
					System.out.println("Your choice: -1 to back");
					choice = scanner.nextInt();
					scanner.nextLine();
					if (choice > avaliable.size()) {
						throw new Exception("invalid choice");
					} else if (choice == -1) {
						break;
					}
					choice--;
					System.out.println("Please enter date (dd-MM)");
					date = scanner.nextLine();
					String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
					date += "-" + year;
					avaliable.get(choice).displaySlots(new SimpleDateFormat("dd-MM-yyyy").parse(date));
					System.out.println("Do you want to book a slot (Y/N)");
					if (scanner.nextLine().equalsIgnoreCase("y")) {
						bookPlayground(avaliable.get(choice), date);
					}
					break;
				} catch (ParseException e) {
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();

				}
			}
			break;
		case 2:
			scanner.nextLine();
			Collections.sort(avaliable, new pphComparator());
			for (int i = avaliable.size() - 1; i >= 0; i--) {
				if (avaliable.get(i).getPlaygroundState() != State.AVALIABLE) {
					avaliable.remove(i);
				}
			}

			while (true) {
				try {
					for (int i = 0; i < avaliable.size(); i++) {
						System.out.println((i + 1) + "-" + avaliable.get(i));
					}
					if (avaliable.size() == 0) {
						System.out.println("no Playgrounds avaliable");
						break;
					}
					System.out.println("Your choice: -1 to back");
					choice = scanner.nextInt();
					scanner.nextLine();
					if (choice > avaliable.size()) {
						throw new Exception("invalid choice");
					} else if (choice == -1) {
						break;
					}
					choice--;
					System.out.println("Please enter date (dd-MM)");
					date = scanner.nextLine();
					String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
					date += "-" + year;
					avaliable.get(choice).displaySlots(new SimpleDateFormat("dd-MM-yyyy").parse(date));
					System.out.println("Do you want to book a slot (Y/N)");
					if (scanner.nextLine().equalsIgnoreCase("y")) {
						bookPlayground(avaliable.get(choice), date);
					}
					break;
				} catch (ParseException e) {
					System.out.println("Invalid date entered");
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 3:
			try {
				scanner.nextLine();
				ArrayList<Playground> matched = timeFilter();
				for (int i = matched.size() - 1; i >= 0; i--) {
					if (matched.get(i).getPlaygroundState() != State.AVALIABLE) {
						matched.remove(i);
					}
				}

				while (true) {
					try {
						for (int i = 0; i < matched.size(); i++) {
							System.out.println((i + 1) + "-" + matched.get(i));
						}
						if (matched.size() == 0) {
							System.out.println("no Playgrounds avaliable");
							break;
						}
						System.out.println("Your choice: -1 to back");
						choice = scanner.nextInt();
						scanner.nextLine();
						if (choice > matched.size()) {
							throw new Exception("invalid choice");
						} else if (choice == -1) {
							break;
						}
						choice--;
						System.out.println("Please enter date (dd-MM)");
						date = scanner.nextLine();
						String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
						date += "-" + year;
						avaliable.get(choice).displaySlots(new SimpleDateFormat("dd-MM-yyyy").parse(date));
						System.out.println("Do you want to book a slot (Y/N)");
						if (scanner.nextLine().equalsIgnoreCase("y")) {
							bookPlayground(matched.get(choice), date);
						}
						break;
					} catch (ParseException e) {
						System.out.println("Invalid date entered");
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("Incorrect date entered");
			}
			break;
		default:

		}
	}

	/**
	 * Filters playgrounds to fit the given time
	 * <p>
	 * Takes These inputs by scanner: Date, From(hour), To(hour). Then filters the
	 * <code>playgrounds</code> to make sure that the given slot(s) is avaliable
	 * </p>
	 * <p>
	 * Accessbile for <code>Player</code> only
	 * </p>
	 * 
	 * @return ArrayList&lt;Playgrounds&gt;matched All the playgrounds that have the
	 *         given slots open
	 * @throws ParseException parsing user input to int for comparison
	 */
	public static ArrayList<Playground> timeFilter() throws ParseException {
		ArrayList<Playground> matched = new ArrayList<Playground>(playgrounds);
		String date, from, to;

		System.out.println("Please enter date (dd-MM)");
		date = scanner.nextLine();
		System.out.println("Please enter starting hour (24-hour format)");
		from = scanner.nextLine();
		System.out.println("Please enter ending hour (24-hour format)");
		to = scanner.nextLine();
		for (int i = matched.size() - 1; i >= 0; i--) {
			if (Integer.parseInt(from) < matched.get(i).getAvaliableTime().from
					|| Integer.parseInt(from) > matched.get(i).getAvaliableTime().to
					|| Integer.parseInt(to) < matched.get(i).getAvaliableTime().from
					|| Integer.parseInt(to) > matched.get(i).getAvaliableTime().to) {
				matched.remove(i);
				continue;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH");
			String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			String fromDate = date + "-" + year + " " + from;
			String toDate = date + "-" + year + " " + to;
			Booking temp = new Booking(formatter.parse(fromDate), formatter.parse(toDate));
			for (int j = matched.get(i).getBookings().size() - 1; j > 0; j--) {
				if (matched.get(i).getBookings().get(j).collides(temp)) {
					matched.remove(i);
					break;
				}
			}
		}
		return matched;
	}

	/**
	 * <code>Player</code> specific menu
	 * <p>
	 * Accessbile for <code>Player</code> only
	 * </p>
	 * 
	 * @throws Exception input validation
	 */
	public static void PlayerMenu() throws Exception {

		System.out.println("Welcome " + accounts.get(currentIndex).getUserName() + " to the Player Mainmenu");
		System.out.println("1- View Playgrounds");
		System.out.println("2- Modify team");
		System.out.println("3- View Bookings");
		System.out.println("4- Logout");
		String choice = scanner.nextLine();
		switch (choice) {
		case "1":
			viewFilter();
			break;
		case "2":
			((Player) accounts.get(currentIndex)).modifyTeam();
			break;
		case "3":
			((Player) accounts.get(currentIndex)).modifyBookings();
			break;
		case "4":
			logout();
			break;
		default:
			throw new Exception("Invalid input");
		}

	}

	/**
	 * <code>PlaygorundOwner</code> specific menu
	 * <p>
	 * Accessbile for <code>PlaygorundOwner</code> only
	 * </p>
	 * 
	 * @throws Exception input validation
	 */
	public static void OwnerMenu() throws Exception {

		System.out.println("Welcome " + accounts.get(currentIndex).getUserName() + " to the Owner Mainmenu");
		System.out.println("1- Add a playground");
		System.out.println("2- Logout");
		switch (scanner.nextLine()) {
		case "1":
			addPlayground();
			break;
		case "2":
			logout();
			break;
		default:
			throw new Exception("Invalid input");

		}
	}

	/**
	 * <code>Administrator</code> specific menu
	 * <p>
	 * Accessbile for <code>Administrator</code> only
	 * </p>
	 * 
	 * @throws Exception input validation
	 */
	public static void adminMenu() throws Exception {

		System.out.println("Welcome " + accounts.get(currentIndex).getUserName() + " to the Administrator Mainmenu");
		System.out.println("1- View playgrounds for approval");
		System.out.println("2- Logout");
		switch (scanner.nextLine()) {
		case "1":
			approveMenu();
			break;
		case "2":
			logout();
			break;
		default:
			throw new Exception("Invalid input");

		}
	}
}
