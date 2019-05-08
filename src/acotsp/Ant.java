package acotsp;

import graphpckg.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * An individual of an AcoTspSimulator, will be associated to a path it is walking on a graph
 * @author g19
 *
 */
public class Ant 
{
	int pathw = 0;
	LinkedList<INode> path = new LinkedList<INode>();
	
	/**
	 * Creates an object ant with its path starting on the nest
	 * @param nest
	 */
	public Ant(INode nest)
	{
		pathw = 0;
		path.add(nest);
	}
	
	/**
	 * Sets destination of a move to itself in a AcoTspSimulator
	 * @param sim the AcoTspSimulator in which this ant is moving
	 * @return destination node
	 */
	Move setmove(IAcoTspSimulator isim, double currenttime)
	{
		AcoTspSimulator sim = (AcoTspSimulator) isim;
		
		/* Then, we need to decide new destination! So we'll follow the rules */
		double traveltime = 0;
		INode destination = null;
		
		ArrayList<INode> J = new ArrayList<INode>();
		J.addAll(path.peekLast().getneigh());
		J.removeAll(path);

		/* no new nodes, uniform */
		if(J.isEmpty())
		{
			ArrayList<INode> neighs = new ArrayList<INode>();
			neighs.addAll(path.peekLast().getneigh());
			destination = neighs.get(Parameters.intuniformRandom(neighs.size()));
			traveltime = Parameters.expRandom(sim.p.delta*sim.g.edgew(path.peekLast(),destination));
		}
		/* new nodes, probabilities based on the formula */
		else
		{
			/* variables to calculate probabilities */
			double[] Pk = new double[J.size()], ck = new double[J.size()];
			double c = 0, land, boat = 0;
			int i = 0;
			INode last = path.peekLast();
			
			/* calculating the probabilities */
			for(i = 0; i < Pk.length; i++)
			{
				ck[i] = (sim.p.alpha + sim.g.edgep(last,J.get(i)))/(sim.p.beta + sim.g.edgew(last,J.get(i)));
				c += ck[i];
			}
			for(i = 0; i < Pk.length; i++)
			{
				Pk[i] = ck[i]/c;
			}
			
			/* generate a random double with uniform distribution */
			land = Parameters.doubleuniformRandom(1.0); i = 0;
			while(boat < land)
			{
				/* the sum of all probabilities is 1 */
				boat += Pk[i];
				i++;
			}
			/* where the boat landed it's the node! notice i++ is before the check */
			destination = J.get(i-1);
			traveltime = Parameters.expRandom(sim.p.delta*sim.g.edgew(last, destination));
		}
		
		return new Move(currenttime+traveltime, this, destination);
	}
}
