package tetPns;

import java.util.Vector;

/**
 * This is the main class that enclose all the Petri net elements
 * @author Michele Tameni, Alessio Troiano
 * @
 */
public class PetriNet {
	
	private Vector arcs;
	private Vector places;
	private Vector transitions;
	
	public PetriNet(){
		arcs = new Vector();
		places = new Vector();
		transitions = new Vector();
	}
	
	public void addArc(Arc a){
		arcs.addElement(a);
	}
	
	public void addPlace(Place p){
		places.addElement(p);
	}
	
	public void addTransition(Transition t){
		transitions.addElement(t);
	}
	
	
}
