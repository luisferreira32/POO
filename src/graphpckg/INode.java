package graphpckg;

import java.util.ArrayList;

/**
 * Interface for a generic node in a graph with the methods deemed necessary for a generic node
 * @author g19
 *
 */
public interface INode
{
	/**
	 * Node index getter
	 * @return idx is node index
	 */
	public int getidx();
	
	/**
	 * Node id getter
	 * @return id is node identification
	 */
	public int getid();
	
	/**
	 * Neighbor adding function, one at a time
	 * @param n new neighbor
	 */
	public void setneigh(INode n);
	
	/**
	 * Neighbor list getter
	 * @return neigh is array list of neighbors
	 */
	public ArrayList<? extends INode> getneigh();
}
