package tetPns;

import java.io.Serializable;
import java.util.Vector;

/**
 * This is the main class that enclose all the Petri net elements
 * @author Michele Tameni, Alessio Troiano
 */
public class PetriNet implements Serializable{

	private Vector<Arc> arcs;
	private Vector<Place> places;
	private Vector<Transition> transitions;
	
	public PetriNet(){
		arcs = new Vector<Arc>();
		places = new Vector<Place>();
		transitions = new Vector<Transition>();
	}
	
	public void addArc(Arc a){
		arcs.addElement(a);
		this.createLinks(a);
	}
	
	public void addPlace(Place p){
		places.addElement(p);
	}
	
	public void addTransition(Transition t){
		transitions.addElement(t);
		this.orderTransitionsByPriority();
	}
	
	/**
	 * Collega ad ogni transizione gli archi in ingresso e gli archi in uscita
	 * 
	 * P.S. Sono fico anch'io!!!
	 */
	public void createLinks(Arc a){
		Transition t= null;

		if(a.getSourceElement() instanceof Transition){
			t=(Transition) a.getSourceElement();
			t.addArcOut(a);
		}
		if(a.getTargetElement() instanceof Transition){
			t=(Transition) a.getTargetElement();
			t.addArcIn(a);
		}
		
	}
	
	//FIXME Metodo per riordinare le transizioni
	// TODO refactor per inserimento ordinato e buoannotte a tutti
	public void orderTransitionsByPriority(){
		Transition ti,tj,temp;
		
		for(int i=0; i<transitions.size();i++)
		{
			ti = transitions.get(i);
			for(int j= (i+1); j<transitions.size(); j++){
				tj = transitions.get(j);
				if(ti.getPriority() > tj.getPriority()){
					temp = transitions.get(i);;
					transitions.setElementAt(tj, i);
					transitions.setElementAt(temp, j);
				}
			}
		}
	}
	
	private Vector<Transition> getEnabledTransitions(){
		Vector<Transition> enabled= new Vector<Transition>();
		
		for(Transition t : transitions){
			if(t.isEnabled())
				enabled.addElement(t);
		}
		
		if(enabled.size() == 0)
			return null;
		else
			return enabled;
		
	}
	
	//FIXME Secondo me c'è un metodo migliore...
	public Vector<Transition> getEnabledTransitionsWithHighestPriority(){
		Vector<Transition> enabled;
		Vector<Transition> v = new Vector<Transition>();
		Transition t;
		int priority;
		
		if((enabled = getEnabledTransitions()) != null){
			t = enabled.firstElement();
			v.addElement(t);
			priority = t.getPriority();
			for(int i=1; i<enabled.size(); i++){ //Se enabled è di un solo elemento?!?!?!?!?!
				t = enabled.get(i);
				if(t.getPriority() == priority)
					v.addElement(t);
				else break;
			}
			return v;
		}
		return null;//Non ci sono transizioni abilitate
	}
	
	public boolean fireTransition(String id){
		for(Transition t : transitions){
			if(t.getId().equals(id)) {
				t.fire();
				return true;
			} 
		}
		return false;
	}

	/**
	 * Che gran bel pezzo di codice!
	 * @param id
	 * @return Place or Transition
	 */
	public Element getElementById(String id) {
		for(Place place : places) {
			if(place.getId().equals(id)) return place;
		}
		for(Transition trans : transitions) {
			if(trans.getId().equals(id)) return trans;
		}
		return null;
	}
	

	public Vector<Place> getPlaces() {
		return places;
	}

	public Vector<Transition> getTransitions() {
		return transitions;
	}
	
	public Vector<Arc> getArcs() {
		return arcs;
	}
	
	public void getInfo() {
		for(Place place : places) {
			place.getInfo();
		}
		
		System.out.println();
		
		for(Transition trans : transitions) {
			trans.getInfo();
		}
		
		for(Arc arc : arcs) {
			arc.getInfo();
		}
	}
	
}
