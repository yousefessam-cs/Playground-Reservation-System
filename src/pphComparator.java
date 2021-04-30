
import java.util.Comparator;

/**
 * used to compare playgrounds pph
 * 
 * @author Ahmed Rushdi Elkilani 20180008 ahmedghjkhk@gmail.com
 *
 */
public class pphComparator implements Comparator<Playground> {
	/**
	 * compare price per hour of lhs' to rhs'
	 * 
	 * @return 1 if lhs' is greater, -1 if smaller, 0 if equals
	 */
	public int compare(Playground lhs, Playground rhs) {
		return lhs.getPph() > rhs.getPph() ? 1 : (lhs.getPph() < rhs.getPph()) ? -1 : 0;
	}
}