package dss;

/**
 * Interface for a generic pending event container with the necessary methods, adding events and retrieving events
 * @author g19
 *
 */
public interface IPEC
{
	/**
	 * Add an event to the pending event container, organizing it by time stamp
	 * @param e
	 */
	public void addEvent(IEvent e);
	/**
	 * Next event to simulate, taken from the pending event container
	 * @return an event
	 */
	public IEvent nextEvent();
	
}
