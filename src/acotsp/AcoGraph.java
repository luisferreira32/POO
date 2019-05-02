package acotsp;

import graphpckg.*;

/**
 * Interface for a graph of the ant colony optimization algorithm, extending a graph interface
 * and adding pheromones to the edges in order to use them in ACO.
 * @author g19
 *
 */
public interface AcoGraph extends Graph
{
	/**
	 * Fetches the value of pheromones in an edge
	 * @param n1 graph node
	 * @param n2 adjacent graph node
	 * @return pheromones in edge connected by n1, n2
	 */
	public double edgep(Object n1, Object n2);
	/**
	 * Sets the pheromones in an edge
	 * @param n1 graph node
	 * @param n2 adjacent graph node
	 * @param p new value of pheromones to the edge
	 */
	public void setp(Object n1, Object n2, double p);
	/**
	 * Returns the sum of the weights from the edges (a so called total weight)
	 * @return
	 */
	public int gettw();
}
