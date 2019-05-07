package acotsp;

import graphpckg.*;
import dss.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * An event specific for the ACOTSPSimulator, so its DSS must be an ACOTSPSimulator
 * @author g19
 *
 */
public class Move extends Event
{
	Ant ant;
	
	/**
	 * @param tic is the time of the event
	 * @param a is the ant that will move
	 */
	public Move(double tic, Ant a)
	{
		super(tic);
		ant = a;
	}
	
	/**
	 * This function runs the Event Move in an DSS expected to be ACOTSPSimulator
	 * An ant moves to a new node based on the weight of the edge, if no new nodes are
	 * available an old node is visited and the path is erased from that point
	 * @param dss must be an ACOTSPSimulator in which we have ants
	 */
	public void run(DSS dss)
	{
		AcoTspSimulator sim = (AcoTspSimulator) dss;
		boolean hcCompleted = false;
		
		/* First check if HC is completed IF a neighbor is the nest && we've been all over the place  */
		for(MNode iter : ant.path.peekLast().getneigh())
		{
			if(iter.equals(ant.path.peekFirst()) && ant.path.size() == sim.p.totalnodes)
			{
				hcCompleted = true;
			}
		}
		
		/* if it is, proceed to store HC & restart ant path */
		if(hcCompleted)
		{
			/* add the new HC*/
			sim.checkHC(new HamCycle(ant.path, ant.pathw + sim.g.edgew(ant.path.peekLast(), sim.g.getnode(sim.p.nestidx))));
			
			/* add pheromone to the path & events to evaporate them*/
			MNode[] apath = ant.path.toArray(new MNode[ant.path.size()]);
			double newpheromones = Parameters.expRandom(sim.p.plevel*sim.g.gettw()/sim.g.pathw(ant.path.toArray(new MNode[ant.path.size()])));
			
			for(int i = 0; i<apath.length-1; i++)
			{
				MNode[] edge = {apath[i], apath[i+1]};
				/* only add a new evap if there is none pending already */
				if(!(sim.g.edgep(edge[0], edge[1]) > 0))
				{
					sim.addPecEvent((IEvent)new Evap(getts() + Parameters.expRandom(sim.p.eta), edge, sim.p.rho));					
				}
				/* and add the pheromones! */
				sim.g.setp(edge[0], edge[1], sim.g.edgep(edge[0],edge[1])+newpheromones);
				sim.g.setp(edge[1], edge[0], sim.g.edgep(edge[1],edge[0])+newpheromones);
			}
			
			/* clear path and get to nest */
			ant.path.clear();
			ant.pathw = 0;
			ant.path.add((MNode)sim.g.getnode(sim.p.nestidx));
		}
		
		/* Then, we need to move! So we'll follow the rules */
		double traveltime = 0;
		MNode destination = null;
		
		ArrayList<MNode> J = new ArrayList<MNode>();
		J.addAll(ant.path.peekLast().getneigh());
		J.removeAll(ant.path);

		/* no new nodes, uniform */
		if(J.isEmpty())
		{
			ArrayList<MNode> neighs = new ArrayList<MNode>();
			neighs.addAll(ant.path.peekLast().getneigh());
			destination = neighs.get(Parameters.intuniformRandom(neighs.size()));
			traveltime = Parameters.expRandom(sim.p.delta*sim.g.edgew(ant.path.peekLast(),destination));
			
			/* so let's clear the wrong path */
			ListIterator<MNode> litr = ant.path.listIterator();
			MNode niter = null, secondvisit = null;
			while( litr.hasNext())
			{
				niter = litr.next();
				if(niter.equals(destination))
				{
					secondvisit = niter;
					break;
				}
			}
			/* the destination will NEVER be the last of a list, not on neigh */
			if(secondvisit != null)
			{
				List<MNode> erase = ant.path.subList(ant.path.indexOf(secondvisit), ant.path.size()-1);
				ant.pathw -= sim.g.pathw(erase.toArray(new MNode[erase.size()]));
				litr.remove();
				while(litr.hasNext())
				{
					niter = litr.next();
					litr.remove();
				}
			}
			/* and add the destination */
			ant.path.add(destination);
		}
		/* new nodes, probabilities based on the formula */
		else
		{
			/* variables to calculate probabilities */
			double[] Pk = new double[J.size()], ck = new double[J.size()];
			double c = 0, land, boat = 0;
			int i = 0;
			MNode last = ant.path.peekLast();
			
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
			
			ant.path.add(destination);
			ant.pathw += sim.g.edgew(last, destination);
			traveltime = Parameters.expRandom(sim.p.delta*sim.g.edgew(last, destination));
		}
		
		/* then set up the timer for the next move on this ant */
		sim.addPecEvent((IEvent)new Move(getts()+traveltime, ant));
	}
}



