
import java.util.Comparator;

/**
 * used to compare playgrounds location with respect to the
 * <code>Player</code>'s address
 * 
 * @author Ahmed Rushdi Elkilani 20180008 ahmedghjkhk@gmail.com
 *
 */
public class locationComparator implements Comparator<Playground> {
	/**
	 * compares the playgrounds' addresses
	 * <p>
	 * Starting with the city, then the neighbourhood, then the street.
	 * </p>
	 */
	public int compare(Playground lhs, Playground rhs) {
		String pStreet = ((Player) system.accounts.get(system.currentIndex)).getLocation().split("-")[0];
		String pNeighbourhood = ((Player) system.accounts.get(system.currentIndex)).getLocation().split("-")[1];
		String pCity = ((Player) system.accounts.get(system.currentIndex)).getLocation().split("-")[2];

		if (pCity.equalsIgnoreCase(lhs.getAddress().split("-")[2])
				&& rhs.getAddress().split("-")[2].equalsIgnoreCase(pCity)) {

			if (pNeighbourhood.equalsIgnoreCase(lhs.getAddress().split("-")[1])
					&& rhs.getAddress().split("-")[1].equalsIgnoreCase(pNeighbourhood)) {

				if (pStreet.equalsIgnoreCase(lhs.getAddress().split("-")[0])
						&& rhs.getAddress().split("-")[0].equalsIgnoreCase(pStreet)) {

					return 0;

				} else if (pStreet.equalsIgnoreCase(lhs.getAddress().split("-")[0])
						&& !rhs.getAddress().split("-")[0].equalsIgnoreCase(pStreet)) {
					return -1;
				} else if (!pStreet.equalsIgnoreCase(lhs.getAddress().split("-")[0])
						&& rhs.getAddress().split("-")[0].equalsIgnoreCase(pStreet)) {
					return 1;
				}

			} else if (pNeighbourhood.equalsIgnoreCase(lhs.getAddress().split("-")[1])
					&& !rhs.getAddress().split("-")[1].equalsIgnoreCase(pNeighbourhood)) {
				return -1;
			} else if (!pNeighbourhood.equalsIgnoreCase(lhs.getAddress().split("-")[1])
					&& rhs.getAddress().split("-")[1].equalsIgnoreCase(pNeighbourhood)) {
				return 1;
			}

		} else if (pCity.equalsIgnoreCase(lhs.getAddress().split("-")[2])
				&& !rhs.getAddress().split("-")[2].equalsIgnoreCase(pCity)) {
			return -1;
		} else if (!pCity.equalsIgnoreCase(lhs.getAddress().split("-")[2])
				&& rhs.getAddress().split("-")[2].equalsIgnoreCase(pCity)) {
			return 1;
		}
		return 0;
	}
}