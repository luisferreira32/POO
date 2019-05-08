package acotsp;

import dss.*;
import graphpckg.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Extension of a discrete stochastic simulator based on a pending event container
 * for the specific case of ant colony optimization solving the traveling salesman
 * @author g19
 *
 */
public class AcoTspSimulator extends DSSPEC implements IAcoTspSimulator
{
	/* attributes */
	private int mevent = 0;
	private int eevent = 0;
	private int obsnum = 0;

	/* connections */
	AcoGraph g;
	Ant[] ants;
	Parameters p = new Parameters();
	HamCycle hc = null;

	/**
	 * Creates a Ant Colony Optimization for Traveling Salesman Problem simulator
	 * @param filename tells us the input parameters
	 */
	public AcoTspSimulator(String filename)
	{
		/* read the file */
		readXML(filename);
		/* create the ants based on the file & set first wave of movements */
		ants = new Ant[p.antcolsize];
		for(int i = 0; i<p.antcolsize; i++)
		{
			ants[i] = new Ant((MNode)g.getnode(p.nestidx));
			addPecEvent((IEvent)ants[i].setmove((IAcoTspSimulator)this, 0));
		}
		/* and pray that main actually starts the simulation */
	}

	/**
	 * Simulates using a PEC (extends a DSS with PEC) and prints observations.
	 * The final instant in this simulator is not decided by user, so parameter
	 * is removed in this overwrite
	 */
	public void simulate()
	{
		IEvent iter = nextPecEvent();
		while(iter != null && iter.getts() < p.finalinst)
		{
			if(iter.getts() > obsnum*(p.finalinst/20))
			{
				printObs();
				obsnum++;
			}
			/* then simulate the event, count it & pick the next */
			iter.run(this);
			if(iter.getClass() == Move.class)
				mevent++;
			else if(iter.getClass() == Evap.class)
				eevent++;
			iter = nextPecEvent();
		}

		while (obsnum < 20)
		{
			printObs();
			obsnum++;
		}
	}

	/**
	 * Reads the XML file received as input through a filename
	 * @param filename
	 */
	public void readXML(String filename)
	{
		try
		{
			DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();			
			DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(new InputSource("./"+filename)); /* CHANGE THIS DEPENDING ON FILE PLACE */
			doc.getDocumentElement().normalize();

			Node root = doc.getDocumentElement();
			Element el;
			/* get main parameters from root */
			el = (Element)root;
			p.finalinst = Double.parseDouble(el.getAttribute("finalinst"));
			p.plevel = Double.parseDouble(el.getAttribute("plevel"));
			p.antcolsize = Integer.parseInt(el.getAttribute("antcolsize"));

			/* variables to check for the graph node and events node */
			NodeList childNodes = root.getChildNodes(), graphNodes = null, eventNodes = null;
			Node currentNode, graph = null, events = null;
			/* iterate through the root elements */
			for(int i = 0; i < childNodes.getLength(); i++)
			{
				currentNode = childNodes.item(i);
				/* to prevent trying to read the white spaces*/
				if(currentNode != null && currentNode.getNodeType() == Node.ELEMENT_NODE)
				{
					if(currentNode.getNodeName() == "graph")
					{
						graph = currentNode;
						graphNodes = graph.getChildNodes();
					}
					else if(currentNode.getNodeName() == "events")
					{
						events = currentNode;
						eventNodes = events.getChildNodes();
					}
				}
			}

			/* read the graph attributes*/
			el = (Element)graph;
			p.nestidx = Integer.parseInt(el.getAttribute("nestnode"))-1; /* for index purposes NEST - 1*/
			p.totalnodes = Integer.parseInt(el.getAttribute("nbnodes"));
			
			/* check */
			if(p.nestidx < 0 || p.totalnodes < 0)
				throw(new Exception("Invalid parameters"));	

			/* set the graph */
			INode[] nodes = new MNode[p.totalnodes];
			int[][] weights = new int[p.totalnodes][p.totalnodes];
			for(int i = 0; i < p.totalnodes; i++)
			{
				nodes[i] = new MNode(i+1,i);
				for(int j = 0; j < p.totalnodes; j++)
				{
					/* invalid weight is -1*/
					weights[i][j] = -1;
				}
			}

			/* auxiliary variables to read the graph weights */
			NodeList nodeWeights; Node witer;
			int nodeidx=0, neighbour=0, weight;
			/*  read the graph  weights */
			for(int i = 0; i < graphNodes.getLength(); i++)
			{
				currentNode = graphNodes.item(i);
				/* to prevent trying to read the white spaces*/
				if(currentNode != null && currentNode.getNodeType() == Node.ELEMENT_NODE)
				{
					nodeWeights = currentNode.getChildNodes();
					el = (Element)currentNode;
					nodeidx = Integer.parseInt(el.getAttribute("nodeidx"));
					/* iterate through the edges */
					for(int j = 0; j < nodeWeights.getLength(); j++)
					{
						witer = nodeWeights.item(j);
						if(witer != null && witer.getNodeType() == Node.ELEMENT_NODE)
						{
							el = (Element) witer;
							neighbour =Integer.parseInt(el.getAttribute("targetnode"));
							weight=Integer.parseInt(el.getTextContent());
							/* set weight */
							weights[nodeidx-1][neighbour-1] = weight;
							weights[neighbour-1][nodeidx-1] = weight;
							/* and neighbor */
							nodes[nodeidx-1].setneigh(nodes[neighbour-1]);
							nodes[neighbour-1].setneigh(nodes[nodeidx-1]);
						}
					}
				}
			}

			/* upon success of reading, let's create the graph */
			this.g = new AcoMGraph(nodes, weights);

			/* and read the events */
			for(int i = 0; i < eventNodes.getLength(); i++)
			{
				currentNode = eventNodes.item(i);
				if(currentNode != null && currentNode.getNodeType() == Node.ELEMENT_NODE)
				{
					el = (Element)currentNode;
					if(currentNode.getNodeName() == "move")
					{
						p.alpha = Double.parseDouble(el.getAttribute("alpha"));
						p.beta = Double.parseDouble(el.getAttribute("beta"));
						p.delta = Double.parseDouble(el.getAttribute("delta"));
					}
					else if(currentNode.getNodeName() == "evaporation")
					{
						p.eta = Double.parseDouble(el.getAttribute("eta"));
						p.rho = Double.parseDouble(el.getAttribute("rho"));
					}
				}
			}
			
			/* check */
			if(p.alpha<0 || p.beta<0 || p.delta<0 || p.eta<0 || p.rho<0 || p.plevel<0 || p.antcolsize<0)
			{
				throw(new Exception("Invalid parameters"));		
			}

		}
		catch (Exception ex)
		{
			System.out.println("Simulator will now exit");
			System.out.println("...");
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Replaces the hamiltonian cycle which we keep track with the lowest weighted HC
	 * @param newhc a new HC to test
	 */
	public void checkHC(HamCycle newhc)
	{
		if(hc == null)
		{
			hc = newhc;
		}
		else if(hc.totalw > newhc.totalw)
		{
			hc = newhc;
		}
	}
	/**
	 * Prints the observation with the shortest HC
	 */
	public void printObs()
	{
		System.out.println("Observation " + (this.obsnum+1));
		System.out.println("     Present instant: " + this.obsnum*(p.finalinst/20));
		System.out.println("     Number of move events: " + this.mevent);
		System.out.println("     Number of evaporaiton events: " + this.eevent);
		System.out.print("     Hamiltonian cycle: ");
		if(hc != null)
			hc.print();
		System.out.println();
	}
}
