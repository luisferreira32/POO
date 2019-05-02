package acotsp;

import dss.*;

/**
 * Interface for the ant colony optimization for the traveling salesman problem simulator
 * The simulate doesn't have a final time stamp, it's overwritten, because the time stamp
 * will be given by the parameters in the XML
 * @author g19
 *
 */
public interface IAcoTspSimulator extends DSS
{
	/**
	 * Simulates the problem with the given parameters
	 */
	public void simulate();
	/**
	 * Fetches the parameters from the XML with the name stored in filename
	 * @param filename is the XML file name
	 */
	public void readXML(String filename);
	/**
	 * Prints observations of the progress in the simulation
	 */
	public void printObs();
}
