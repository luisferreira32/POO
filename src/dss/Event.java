package dss;

/**
 * A general time stamped event
 * @author g19
 *
 */
public abstract class Event implements IEvent
{
	double timestamp;
	
	public Event(double t)
	{
		timestamp = t;
	}
	
	/* polymorphic function */
	public abstract void run(DSS env);
	
	/**
	 * A getter for the time stamp
	 * @return time stamp of event
	 */
	public double getts()
	{
		return timestamp;
	}
}
