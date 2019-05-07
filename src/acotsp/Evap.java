package acotsp;

import graphpckg.*;
import dss.*;

/**
 * An event specific for the ACOTSPSimulator, so its DSS must be an ACOTSPSimulator
 * @author g19
 *
 */
public class Evap extends Event
{
	INode[] edge;
	double quantity;
	
	/**
	 * @param tic time of the event
	 * @param quant is quantity of pheromone to evaporate 
	 */
	public Evap(double tic, INode[] e, double quant)
	{
		super(tic);
		edge = e;
		quantity = quant;
	}
	
	/**
	 * @param dss is the simulator in which we are running
	 */
	public void run(DSS dss)
	{
		AcoTspSimulator sim = (AcoTspSimulator) dss;
		/* only upper triangular matters */
		sim.g.setp(edge[0], edge[1], sim.g.edgep(edge[0],edge[1])-quantity);
		sim.g.setp(edge[1], edge[0], sim.g.edgep(edge[1],edge[0])-quantity);
		if(sim.g.edgep(edge[0],edge[1]) <= 0)
		{
			sim.g.setp(edge[0], edge[1], 0);
			sim.g.setp(edge[1], edge[0], 0);
		}
		else
		{
			sim.addPecEvent((IEvent)new Evap(getts() + Parameters.expRandom(sim.p.eta), edge, sim.p.rho));
		}
	}
}
