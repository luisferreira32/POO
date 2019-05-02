package graphpckg;

/**
 * Implementation of a weighted graph in matrix form, using matrix nodes (class MNodes)
 * @author g19
 *
 */
public class MGraph implements Graph
{
	MNode[] nodes;
	int[][] weights;
	
	/**
	 * Constructor of a matrix based graph given the nodes and the weights between
	 * them in matrix form
	 * @param n nodes
	 * @param w weight matrix
	 */
	public MGraph( MNode[] n, int[][] w)
	{
		weights = w;
		nodes = n;
	}
	
	/**
	 * Retrieves the weight from n1 to n2 assuming they are neighbors
	 * @param n1 node1
	 * @param n2 node2
	 */
	public int edgew(Object n1, Object n2)
	{
		MNode mn1 = (MNode)n1; MNode mn2 = (MNode)n2;
		return weights[mn1.idx][mn2.idx];
	}
	
	/**
	 * Retrieves the weight of a path made by adjacent nodes
	 * @param path
	 */
	public int pathw(Object[] path)
	{
		MNode[] mpath = (MNode[]) path;
		int totalw = 0;
		
		for(int i = 0; i < path.length-1; i++)
			totalw += weights[mpath[i].idx][mpath[i+1].idx];
		
		return totalw;
	}
	
	/**
	 * getter for a node of a graph based on his index
	 * @param index
	 * @return node
	 */
	public MNode getnode(int index)
	{
		return nodes[index];
	}
}
