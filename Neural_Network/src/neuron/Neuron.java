package neuron;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Neuron {

	//Possible ids for a Neuron at a paticular depth
	private static Integer[] maxids = { 0, 1, 2, 3, 4 };
	//Maps potential value to Stage of a neuron
	private static HashMap<Integer, String> map = new HashMap<Integer, String>();

	//Class describing state of a neuron
	public class State {
		public int pot;
		public String stage;

		State(int p, String s) {
			pot = p;
			stage = s;
		}
	}

	// State of a Neuron
	State s;
	// Set of incoming syanpses: (parent id, weight) Hashmap
	private HashMap<Integer, Integer> parents;
	// Unique ID of a particular neuron at a particular depth
	private int id;
	// Number of dendrite links
	private int noOfDendrites;
	// Number of synaptic links
	private int noOfSynapses;
	// List of all synaptic links
	private ArrayList<Neuron> links;
	// Depth of a particular Neuron
	private int depth;

	//Maps potential values to stages
	public static void setMap() {
		map.put(-70, "Resting Potential");
		map.put(-65, "Evaluating stimulus");
		map.put(-55, "Threshold potential");
		map.put(30, "Action Potential");
		map.put(-75, "Refractory Period");
	}

	//Constructor
	public Neuron(int nDendrites, int d, int i,
			HashMap<Integer, Integer> incoming) {
		noOfDendrites = nDendrites;
		noOfSynapses = 0;
		links = new ArrayList<Neuron>();
		depth = d;
		id = i;
		s = new State(-70, "Resting Potential"); // assuming initial Resting
													// Potential
		parents = new HashMap<Integer, Integer>();
		if (nDendrites > 0)
			parents.putAll(incoming);

	}

	//Increments number of dendrites
	public void incDendrites(HashMap<Integer, Integer> parent) {
		noOfDendrites++;
		parents.putAll(parent);
	}

	
	//Returns ID of a neuron
	public int getId() {
		return id;
	}

	//Returns synaptic links of a Neuron
	public ArrayList<Neuron> getLinks() {
		return links;
	}

	//Returns number of synapses of a Neuron
	public int getNoOfSynapses() {
		return noOfSynapses;
	}

	//Returns depth of a Neuron
	public int getDepth() {
		return depth;
	}

	//Updates weights for dendritic links
	public void updateWeights(HashMap<Integer, Integer> hm) {
		parents.putAll(hm);
	}

	//Updates state of a Neuron at a time step t
	public void updateState(int t) {
		
		//From source Neuron, signal is propagated forwards after 2 time steps
		int T = t - 2 * depth;
		
		if(T>=0)
		{
			if(T<=7)
			{
				if(T==0)
					s.pot = -70;
				else if(T == 1)
					s.pot = -65;
				else if(T == 2)
					s.pot = -55;
				else if((T >= 3) && (T <= 5))
				{
					if (noOfDendrites > 0)
					checkpropagation(30);
					else
						s.pot = 30;
				}
				else
					s.pot = -75;
			}
			else
			{
				if(noOfDendrites > 0)
				checkpropagation(-70);
				else
					s.pot = -70;
					
			}
			s.stage = map.get(s.pot);
		}
		
		
	}
	
	public void checkpropagation(int pval)
	{
		int sum = 0;
		// Check if stimulus was string enough to propagate signal
		for (Integer val : parents.values())
			sum += val;
		if (sum > 0)
			s.pot = pval;
		else
		{
			s.pot = -70;
			HashMap<Integer,Integer> h = new HashMap<Integer,Integer>();
			for(Neuron n1:links)
			{
				h.clear();
				h.put(id, 0);
				n1.updateWeights(h);
			}
		}
	}
	

	//Adds a synaptic link to a Neuron
	public ArrayList<Neuron> addLinks(int d, ArrayList<Neuron> neurons) {
		int length = neurons.size();
		List<Integer> temp = Arrays.asList(maxids);

		// We give 5/6 proabability of release of an excitatory neurotransmitter
		// and 1/6 for an inhibitory neurotransmitter.
		int weight = (int) (Math.random() * 6 + 1) > 5 ? -1 : 1;
		Collections.shuffle(temp);

		noOfSynapses = (int) (Math.random() * 3 + 1);
		int newId = length;

		HashMap<Integer, Integer> h = new HashMap<Integer, Integer>();
		h.put(this.id, weight);

		//The link can be established with an existing Neuron at the next depth, or with 
		//a new Neuron at that depth
		for (int i = 0; i < noOfSynapses; i++) {
			int k = temp.get(i);
			if (k < newId) {
				Neuron n = neurons.get(k);
				links.add(n);
				n.incDendrites(h);
			} else {

				Neuron n = new Neuron(1, d, newId, h);
				links.add(n);
				neurons.add(n);
				newId++;
			}

		}

		return neurons;
	}

}
