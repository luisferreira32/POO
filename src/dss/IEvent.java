package dss;

/**
 * Interface for a generic Event that has a time stamp and runs on a discrete stochastic simulator
 * @author g19
 *
 */
public interface IEvent
{
	/**
	 * Gets the time stamp of the event
	 * @return time stamp
	 */
	public double getts();
	/**
	 * Runs the event in the simulator, making changes and queuing events according to its
	 * own definition. So it's a polymorphic method that varies depending on the event type.
	 * @param dss is the simulator where the event is running
	 */
	public abstract void run(DSS dss);
}
