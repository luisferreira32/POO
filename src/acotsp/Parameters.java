package acotsp;

import java.util.Random;

/**
 * Auxiliary class with parameters needed to run an ACOTSPSimulator
 * @author g19
 *
 */
class Parameters
{
	double alpha;
	double beta;
	double delta;
	double eta;
	double rho;
	
	double plevel;
	int antcolsize;
	int totalnodes;
	int nestidx;
	double finalinst;
	
	static Random random = new Random(System.currentTimeMillis());
	
	/**
	 * Does a exponential random distribution with mean m
	 * @param m
	 * @return expRandomDist
	 */
	public static double expRandom(double m)
	{
		double next = random.nextDouble();
		return -m*Math.log(1.0-next);
	}
	
	/**
	 * Does an integer uniform distribution with maximum value max
	 * @param max
	 * @return intuniformRandomDist
	 */
	public static int intuniformRandom(int max)
	{
		return random.nextInt(max);
	}
	
	/**
	 * Does a floating point uniform distribution with maximum value max
	 * @param max
	 * @return doubleuniformRandomDist
	 */
	public static double doubleuniformRandom(double max)
	{
		return random.nextDouble()*max;
	}
}
