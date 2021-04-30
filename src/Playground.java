import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Hazem Tarek 20180082
 *
 */
public class Playground {
	private String name;
	private String Address;
	private double pph;
	private int cancelPeriod;
	private Avaliability avaliableTime;
	private ArrayList<Booking> bookings;
	private State playgroundState = State.WAITING;
	private PlaygroundOwner owner;

	Playground(String name, String address, double pph) throws Exception {
		this.name = name;
		Address = address;
		this.pph = pph;
		System.out.println("The Playground is avaliable from (in 24 hour format):");
		int temp = system.scanner.nextInt();
		system.scanner.nextLine();
		System.out.println("to");
		int temp2 = system.scanner.nextInt();
		system.scanner.nextLine();
		System.out.println("Enter cancellation deadline in days before the booked day");
		cancelPeriod = system.scanner.nextInt();
		system.scanner.nextLine();
		bookings = new ArrayList<Booking>();
		avaliableTime = new Avaliability(temp, temp2);

	}

	public int getCancelPeriod() {
		return cancelPeriod;
	}

	public void setCancelPeriod(int cancelPeriod) {
		this.cancelPeriod = cancelPeriod;
	}

	public PlaygroundOwner getOwner() {
		return owner;
	}

	public void setOwner(PlaygroundOwner owner) {
		this.owner = owner;
	}

	public ArrayList<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(ArrayList<Booking> bookings) {
		this.bookings = bookings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public double getPph() {
		return pph;
	}

	public void setPph(double pph) {
		this.pph = pph;
	}

	public Avaliability getAvaliableTime() {
		return avaliableTime;
	}

	public void setAvaliableTime(Avaliability avaliableTime) {
		this.avaliableTime = avaliableTime;
	}

	public State getPlaygroundState() {
		return playgroundState;
	}

	public void setPlaygroundState(State playgroundState) {
		this.playgroundState = playgroundState;
	}

	class Avaliability {
		public int from;
		public int to;

		Avaliability(int F, int T) throws Exception {
			if (F >= 0 && T >= 0 && F <= 24 && T <= 24) {
				from = F;
				to = T;
			} else {
				throw new Exception("Invalid hours");
			}

		}

		public String toString() {
			return "Avaliability [from " + from + " to " + to + "]";
		}
	}

	public String toString() {
		return "Playground [name=" + name + ", Address=" + Address + ", pph=" + pph + ", avaliableTime=" + avaliableTime
				+ ", playgroundState=" + playgroundState + "]";
	}

	/**
	 * displays all avaliable slots in a certain day
	 * 
	 * @param day Day to display
	 */
	public void displaySlots(Date day) {

		boolean[] hours = new boolean[24];
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(day);
		for (int i = 0; i < bookings.size(); i++) {
			cal2.setTime(bookings.get(i).getFrom());
			if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
				int temp = Integer.parseInt(new SimpleDateFormat("HH").format(bookings.get(i).getFrom()));
				int temp2 = Integer.parseInt(new SimpleDateFormat("HH").format(bookings.get(i).getTo()));
				Date begin = cal2.getTime();
				for (int j = temp; j <= temp2; j++) {

					cal2.add(Calendar.HOUR, 1);
					Date toTest = cal2.getTime();
					Booking b = new Booking(begin, toTest);
					if (b.collides(bookings.get(i))) {
						hours[j] = true;
					}
					begin = toTest;
				}
			}
		}
		int start = avaliableTime.from, end;

		for (end = avaliableTime.from; end <= avaliableTime.to; end++) {

			if (end == 24) {
				end++;
				break;
			}
			if (hours[start] != hours[end]) {
				System.out.println(start + " to " + (end) + " is " + (hours[start] ? "Booked" : "avaliable"));
				start = end;
			}
		}

		System.out.println(start + " to " + (end - 1) + " is " + (hours[start] ? "Booked" : "avaliable"));

	}
}
