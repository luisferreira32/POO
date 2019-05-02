package graphpckg;

import java.util.ArrayList;

/**
 * A node for the matrix implementation of a weighted graph
 * @author g19
 *
 */
public class MNode
{
	int id;
	int idx;
	ArrayList<MNode> neigh;
	
	/**
	 * Creates a node for a matrix based graph, therefore, needs an index for the matrix
	 * and also has an identification to distinguish it
	 * @param identification
	 * @param index
	 */
	public MNode(int identification, int index)
	{
		id = identification;
		idx = index;
		neigh = new ArrayList<MNode>();
	}
	
	/**
	 * Node index getter
	 * @return idx is node index
	 */
	public int getidx()
	{
		return idx;
	}
	
	/**
	 * Node id getter
	 * @return id is node identification
	 */
	public int getid()
	{
		return id;
	}
	
	/**
	 * Neighbor adding function, one at a time
	 * @param n new neighbor
	 */
	public void setneigh(MNode n)
	{
		neigh.add(n);
	}
	
	/**
	 * Neighbor list getter
	 * @return neigh is array list of neighbors
	 */
	public ArrayList<MNode> getneigh()
	{
		return neigh;
	}
}
