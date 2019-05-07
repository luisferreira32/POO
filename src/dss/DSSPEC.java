package dss;

/**
 * Implementation of a discrete stochastic simulator with a pending event container
 * @author g19
 *
 */
public class DSSPEC implements DSS
{
	IPEC pec = new PEC();
	
	/**
	 * Does a discrete stochastic simulation based on a pending event contained
	 * @param finalinst is the last instant of the simulation
	 */
	public void simulate(double finalinst)
	{
		IEvent iter = pec.nextEvent();
		while(iter != null && iter.getts() < finalinst)
		{
			/* then simulate the event & get the next */
			iter.run(this);
			iter = pec.nextEvent();
		}
	}
	
	/**
	 * Adding an event e to the pending event container
	 * @param e
	 */
	public void addPecEvent(IEvent e)
	{
		pec.addEvent(e);
	}
	
	/**
	 * Retrieving the first event of the pending event container
	 * @return
	 */
	public IEvent nextPecEvent()
	{
		return pec.nextEvent();
	}
}
