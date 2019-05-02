package acotsp;

import graphpckg.*;

/**
 * Extension of a matrix based weighted graph, specific for the ACOTSPSimulator, therefore
 * the need for a matrix of pheromones
 * @author g19
 *
 */
public class AcoMGraph extends MGraph implements AcoGraph
{
	double[][] pheromones;
	int totalw = 0;
	
	/**
	 * Constructs a Matrix based Graph for the Aco simulator
	 * @param n are the nodes of the graph
	 * @param w are the weights of the edges
	 */
	public AcoMGraph(MNode[] n, int[][] w)
	{
		super(n,w);
		/* should be a zeroed square matrix */
		pheromones = new double[w.length][w[0].length];
		/* only the upper triangular matters, when there is no connection weight is -1 */
		for(int i = 0; i < w.length; i ++)
		{
			for(int j = i; j < w[0].length; j++)
			{
				if(w[i][j] != -1)
				{
					totalw += w[i][j];
				}
			}
		}
	}
	
	/**
	 * Sets level of pheromone to a edge that connects node n1 and node n2
	 * @param n1 matrix graph node
	 * @param n2 adjacent matrix graph node
	 * @param p pheromones to set
	 */
	public void setp(Object n1, Object n2, double p)
	{
		MNode mn1 = (MNode)n1;
		MNode mn2 = (MNode)n2;
		pheromones[mn1.getidx()][mn2.getidx()] = p;
	}
	
	/**
	 * Getter of pheremone level in edge connected by n1 and n2
	 * @param n1 matrix graph node
	 * @param n2 adjacent matrix graph node
	 * @return level of pheromones in that edge
	 */
	public double edgep(Object n1, Object n2)
	{
		MNode mn1 = (MNode)n1;
		MNode mn2 = (MNode)n2;
		return pheromones[mn1.getidx()][mn2.getidx()];
	}
	
	/**
	 * A function to give the total weight of the graph edges
	 * @return total weight
	 */
	public int gettw()
	{
		return totalw;
	}
}
