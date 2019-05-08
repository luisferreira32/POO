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
	INode dest;
	
	/**
	 * @param tic is the time of the event
	 * @param a is the ant that will move
	 */
	public Move(double tic, Ant a, INode d)
	{
		super(tic);
		ant = a;
		dest = d;
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
		
		/* The hc is completed if the destination is the NEST and all nodes have been visited */
		if(dest.equals(sim.g.getnode(sim.p.nestidx)) && ant.path.size() == sim.p.totalnodes)
		{
			/* add the new HC*/
			sim.checkHC(new HamCycle(ant.path, ant.pathw + sim.g.edgew(ant.path.peekLast(), sim.g.getnode(sim.p.nestidx))));
			
			/* add pheromone to the path & events to evaporate them*/
			INode[] apath = ant.path.toArray(new MNode[ant.path.size()]);
			double newpheromones = Parameters.expRandom(sim.p.plevel*sim.g.gettw()/sim.g.pathw(ant.path.toArray(new MNode[ant.path.size()])));
			
			for(int i = 0; i<apath.length-1; i++)
			{
				INode[] edge = {apath[i], apath[i+1]};
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
		
		/* Then we move */
		
		/* so let's clear the wrong path if the destination has already been visited */
		ListIterator<INode> litr = ant.path.listIterator();
		INode niter = null, secondvisit = null;
		while( litr.hasNext())
		{
			niter = litr.next();
			if(niter.equals(dest))
			{
				secondvisit = niter;
				break;
			}
		}
		
		/* the destination will NEVER be the last of a list, not on neigh */
		if(secondvisit != null)
		{
			List<INode> erase = ant.path.subList(ant.path.indexOf(secondvisit), ant.path.size()-1);
			ant.pathw -= sim.g.pathw(erase.toArray(new MNode[erase.size()]));
			litr.remove();
			while(litr.hasNext())
			{
				niter = litr.next();
				litr.remove();
			}
			/* and add the destination */
			ant.path.add(dest);
		}
		/* if there was no second visit it's just a normal move */
		else
		{			
			ant.path.add(dest);
			ant.pathw += sim.g.edgew(ant.path.peekLast(), dest);			
		}
		
		/* then set up the timer for the next move on this ant */
		sim.addPecEvent((IEvent)ant.setmove((IAcoTspSimulator)sim, getts()));
	}
}



