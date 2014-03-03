/**
 * Creates a Neural Network
 */
package neuron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import neuron.Neuron.State;

/**
 * @author Soumya Batra
 *
 */
public class Network {
	
	static int i = 1, n;
	Neuron source;
	LinkedHashMap<Integer,LinkedHashMap<Integer,State>> hm;
	
	//Adds synaptic links
	private void add(ArrayList<Neuron> neurons, ArrayList<Neuron> neuronNext, int depth)
	{
		if(i>=n)
			return;
		
		for(Neuron n1:neurons)
		{
		n1.addLinks(depth, neuronNext);
		}
		
		i+=neuronNext.size();	
		add(neuronNext, new ArrayList<Neuron>(), depth + 1);
		
	}
	public Network(int no)
	{
		int depth = 0;
		n = no;
		Neuron.setMap();
		hm = new LinkedHashMap<Integer,LinkedHashMap<Integer,State>>();
		source = new Neuron(0, 0, 0, new HashMap<Integer,Integer>());
		ArrayList<Neuron> n1 = new ArrayList<Neuron>();
		ArrayList<Neuron> n2 = new ArrayList<Neuron>();
		n1.add(source);
		
		add(n1,n2, depth+1);
		
	}
	
	//Represents a Network in terms of member neurons' depths, ids and states
	public LinkedHashMap<Integer,LinkedHashMap<Integer,State>> getState(int t)
	{
	    LinkedHashMap<Integer,State> h = new LinkedHashMap<Integer,State>();
	
		//Updates states of all Neurons at time step t
	    source.updateState(t);
	    h.put(source.getId()+1, source.s);
	    hm.put(source.getDepth(), h);
	    
	    getStates(source.getLinks(),t);
	    
	    return hm;

	    }

//Updates states of all neurons at a particular depth
	public void getStates(ArrayList<Neuron> n, int t)
	    {
		
		HashSet<Neuron> next = new HashSet<Neuron>();
		ArrayList<Neuron> n2 = new ArrayList<Neuron>();
		LinkedHashMap<Integer,State> h = new LinkedHashMap<Integer,State>();
	    	  	
		    for(Neuron n1:n)
		    {
		    n1.updateState(t);
		    
		    h.put(n1.getId()+1, n1.s);
		    
		    if (n1.getNoOfSynapses()>0)
		    next.addAll(n1.getLinks());
		    
		    } 
		    
		    hm.put(n.get(0).getDepth(),h);
		    if(next.size()>0)
		    {
		    	n2.addAll(next);
		    	getStates(n2,t);
		    }
		    
	    }
	
	//Returns source signal neuron of the network
	public Neuron getSource()
	{
		return source;
	}

}
