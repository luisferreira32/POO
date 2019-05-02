package tester;

import acotsp.*;

/**
 * Main to test the acotsp simulator package
 * @author g19
 *
 */
public class Main 
{
	public static void main(String[] args)
	{
		if(args == null)
			System.exit(1);
		
		IAcoTspSimulator simulator = new AcoTspSimulator(args[0]);
		simulator.simulate();
	}
}
