package acotsp;

import graphpckg.*;

import java.util.LinkedList;

/**
 * An individual of an ACOTSPSimulator, will be associated to a path it is walking on a graph
 * @author g19
 *
 */
public class Ant 
{
	int pathw = 0;
	LinkedList<MNode> path = new LinkedList<MNode>();
	
	/**
	 * Creates an object ant with its path starting on the nest
	 * @param nest
	 */
	public Ant(MNode nest)
	{
		pathw = 0;
		path.add(nest);
	}
}
