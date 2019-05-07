package acotsp;

import graphpckg.INode;
import java.util.LinkedList;

/**
 * stores Halmiltonian cycles based on an ant path of a ACOTSPSimulator
 * @author g19
 *
 */
public class HamCycle
{
	int totalw;
	int[] path;
	
	/**
	 * Creates an HC object to store a path in less memory
	 * @param p is the HC path
	 * @param tw is the total weight of the path
	 */
	public HamCycle(LinkedList<INode> p, int tw)
	{
		totalw = tw;
		path = new int[p.size()];
		int i = 0;
		for(INode niter : p)
		{
			path[i] = niter.getid();
			i++;
		}
	}
	
	/**
	 * prints to terminal the HC in the format {nodeid1, nodeid2, ..., nodeidn}
	 */
	void print()
	{
		Integer nodeidx; int i = 0;
		System.out.print("{");
		for(i = 0; i<path.length-1; i++)
		{
			nodeidx = path[i];
			System.out.print(nodeidx.toString() + ',');			
		}
		nodeidx = path[i];
		System.out.print(nodeidx.toString());
		System.out.print("}");
	}
}
