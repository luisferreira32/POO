package dss;

/**
 * Interface for an implemented discrete stochastic simulator
 * @author g19
 *
 */
public interface DSS
{
	/**
	 * Simulates events according to time order until a final instant
	 * @param finalinst is the final instant of the simulation
	 */
	public void simulate(double finalinst);
}
