package tetPns;

import java.util.Vector;

/**
 * This is the main class that enclose all the Petri net elements
 * @author Michele Tameni, Alessio Troiano
 * @
 */
public class PetriNet {
	
	private Vector<Arc> arcs;
	private Vector<Place> places;
	private Vector<Transition> transitions;
	
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
	
	
	/**
	 * Che gran bel pezzo di codice!
	 * @param id
	 * @return
	 */
	public Object getElementById(String id) {
		for(Place place : places) {
			if(place.getId().equals(id)) return place;
		}
		for(Transition trans : transitions) {
			if(trans.getId().equals(id)) return trans;
		}
		System.out.println("Not Match");
		return null;
	}

	public void getInfo() {
		for(Place place : places) {
			place.getInfo();
		}
		
		for(Transition trans : transitions) {
			trans.getInfo();
		}
		
		for(Arc arc : arcs) {
			arc.getInfo();
		}
		
	}
	
}
