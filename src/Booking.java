
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 
 * @author Hazem Tarek 20180082
 *
 */
public class Booking {

	private Date From;
	private static int counter = 1000000;
	private int bookingID;
	private int duration;
	private Date To;
	private Playground place;
	private ArrayList<Player> booker;

	public Booking(Date from, Date to, Playground place, ArrayList<Player> booker) {
		From = from;
		To = to;
		duration = Integer.parseInt(new SimpleDateFormat("HH").format(to))
				- Integer.parseInt(new SimpleDateFormat("HH").format(from));
		this.place = place;
		this.booker = booker;
		bookingID = counter++;
	}

	public Booking(Date from, Date to) {
		From = from;
		To = to;
		bookingID = counter++;
	}

	public int getBookingID() {
		return bookingID;
	}

	public ArrayList<Player> getBooker() {
		return booker;
	}

	public void setBooker(ArrayList<Player> booker) {
		this.booker = booker;
	}

	public Date getFrom() {
		return From;
	}

	public void setFrom(Date from) {
		From = from;
	}

	public Date getTo() {
		return To;
	}

	public void setTo(Date to) {
		To = to;
	}

	public Playground getPlace() {
		return place;
	}

	public void setPlace(Playground place) {
		this.place = place;
	}

	/**
	 * checks if this booking collides with the given booking
	 * 
	 * @param rhs booking to check
	 * @return true if the <code>From</code> and <code>To</code> dates collide with
	 *         each other
	 */
	public boolean collides(Booking rhs) {
		if (From.compareTo(rhs.From) >= 0 && From.compareTo(rhs.To) < 0)
			return true;
		else if (To.compareTo(rhs.From) > 0 && To.compareTo(rhs.To) <= 0)
			return true;
		return false;
	}

	public int getDuration() {
		return duration;
	}

	/**
	 * sends invites to the Players' emails
	 * <p>
	 * this doesn't send invites to the actual email, but to a fake inbox that isn't
	 * used in the main program
	 * </p>
	 */
	public void invitePlayers() {
		for (int i = 1; i < booker.size(); i++) {
			booker.get(i).getInbox().add(0,
					booker.get(0).getUserName() + " (" + booker.get(0).getEmail() + ") \n has invited you to " + place
							+ " From " + new SimpleDateFormat("dd-MM HH").format(From) + "To "
							+ new SimpleDateFormat("dd-MM HH").format(To));
		}
		booker.get(0).getInbox().add(0,
				"you Have Booked " + place + "\n From " + new SimpleDateFormat("dd-MM HH").format(From) + " To "
						+ new SimpleDateFormat("dd-MM HH").format(To) + " For " + (double) place.getPph() * duration);
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("Booking [From=" + new SimpleDateFormat("dd-MM HH").format(From) + ", To="
				+ new SimpleDateFormat("dd-MM HH").format(To) + ", bookingID=" + bookingID + ", duration=" + duration
				+ ", place=" + place + "]");
		str.append("\nPlayers \n=================\n");
		for (Player i : booker) {
			str.append(i);
			str.append("\n");
		}
		return str.toString();
	}

}
