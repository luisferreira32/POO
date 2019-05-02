package graphpckg;

/**
 * Interface to a weighted graph made of Object nodes
 * @author g19
 *
 */
public interface Graph
{
	/**
	 * Fetches the weight in an edge connected between two adjacent nodes
	 * @param n1 graph node
	 * @param n2 adjacent graph node
	 * @return weight on edge that connects n1, n2
	 */
	public int edgew(Object n1, Object n2);
	/**
	 * Gives the weight of a path in a graph between adjacent nodes
	 * @param path made of adjacent nodes
	 * @return sum of the weight of the edges (a total weight)
	 */
	public int pathw(Object[] path);
	/**
	 * Returns the node object reference of a node indexed in the graph
	 * @param index of the node
	 * @return node reference
	 */
	public Object getnode(int index);
}
