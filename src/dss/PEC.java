package dss;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * A pending event container which contains events of type Event
 * @author g19
 *
 */
public class PEC implements IPEC
{
	LinkedList<IEvent> eventList= new LinkedList<IEvent>();

	/**
	 * Adds elements in an orderly way
	 * @param e
	 */
	public void addEvent(IEvent e)
	{
		ListIterator<IEvent> litr = eventList.listIterator();
		IEvent iter = eventList.peekFirst();
		while(litr.hasNext())
		{
			iter = litr.next();
			if(iter.getts() > e.getts())
			{
				litr.previous();
				break;
			}
		}
		litr.add(e);
	}
	
	/**
	 * Removes first on queue if the queue is not empty
	 * @return event or null
	 */
	public IEvent nextEvent()
	{
		if(!eventList.isEmpty())
			return eventList.pop();
		else
			return null;
	}
}
