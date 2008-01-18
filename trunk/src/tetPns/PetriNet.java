package tetPns;

import java.util.Vector;

/**
 * This is the main class that enclose all the Petri net elements
 * @author Michele Tameni, Alessio Troiano
 */
public class PetriNet {
	
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
	}
	
	public void addPlace(Place p){
		places.addElement(p);
	}
	
	public void addTransition(Transition t){
		transitions.addElement(t);
	}
	
	/**
	 * Collega ad ogni transizione gli archi in ingresso e gli archi in uscita
	 * 
	 * P.S. Sono fico anch'io!!!
	 */
	public void createLinks(){
		Transition t= null;
		for(Arc a : arcs){
			if(a.getSourceElement() instanceof Transition){
				t=(Transition) a.getSourceElement();
				t.addArcOut(a);
			}
			if(a.getTargetElement() instanceof Transition){
				t=(Transition) a.getTargetElement();
				t.addArcIn(a);
			}
		}
	}
	
	//FIXME Metodo per riordinare le transizioni
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
			for(int i=1; i<enabled.size(); i++){
				t = enabled.get(i);
				if(t.getPriority() == priority)
					v.addElement(t);
				else
					return v;
			}
		}
		return enabled;
	}
	
	//FIXME In verità deve scattare in base all'id della transizione.
	public void fireTransition(String id){
		for(Transition t : transitions){
			if(t.getId().equals(id))
				t.fire();
		}
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
	
	public void setPriorities(){
		transitions.get(0).setPriority(0);
		transitions.get(1).setPriority(0);
	}
	
}
