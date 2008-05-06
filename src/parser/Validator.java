package parser;

import java.util.Vector;

import tetPns.Arc;
import tetPns.PetriNet;
import tetPns.Place;
import tetPns.Transition;


/**
 * Validator si occupa di validare la rete di Petri
 * appena immessa. Controlla alcune caratteristiche
 * utili per una buona simulazione.
 * 
 * @author Alessio
 */
public class Validator {
	public static boolean validate(PetriNet pn){
		return (placeController(pn) && transitionController(pn) && arcController(pn) && netIdController(pn));
	}
	
	/**
	 * Controlla che non ci siano id duplicati all'interno
	 * della rete
	 */
	private static boolean netIdController(PetriNet pn){
		Vector<Place> place=pn.getPlaces();
		Vector<Transition> trans=pn.getTransitions();
		Place pi,pj;
		Transition ti,tj;
		
		//Controllo id tra place
		for(int i=0;i<place.size();i++){	
			pi=place.elementAt(i);
			for(int j=(i+1);i<place.size();i++){
				pj=place.elementAt(j);
				if(pi.getId().equalsIgnoreCase(pj.getId()))
					return false;
			}
		}
		
		//Controllo id tra transition
		for(int i=0;i<trans.size();i++){	
			ti=trans.elementAt(i);
			for(int j=(i+1);i<trans.size();i++){
				tj=trans.elementAt(j);
				if(ti.getId().equalsIgnoreCase(tj.getId()))
					return false;
			}
		}
		
		//Controllo id tra place e transition
		for(Transition t : trans){			
			for(Place p : place){
				if(t.getId().equalsIgnoreCase(p.getId()))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * Controlla che gli tutti gli archi siano connessi
	 * ad un qualche elemento
	 */
	private static boolean arcController(PetriNet pn){
		for(Arc a : pn.getArcs()){
			if(a.getSourceElement() instanceof Place && !(a.getTargetElement() instanceof Transition))
				return false;
			if(a.getSourceElement() instanceof Transition && !(a.getTargetElement() instanceof Place))
				return false;
		}
		return true;
	}
	
	/**
	 * Effettua vari controlli sulle transizioni:
	 * 1 - tutte le priorità devono essere >=0
	 * 2 - ogni transizione deve avere almeno un arco in ingresso
	 *     e almeno uno in uscita.
	 * @param pn
	 * @return
	 */
	private static boolean transitionController(PetriNet pn){
		for(Transition t : pn.getTransitions()){
			if(t.getPriority()<0)
				return false;	//Problemi con le priorità
			if(t.getArcIn().size()<=0 || t.getArcOut().size()<=0)
				return false;	//Problemi con gli archi
		}
		return true;	//Tutto ok!!!
	}
	
	/**
	 * Controlla che tutti i place abbiamo un numero
	 * di token >=0;
	 * @param pn
	 * @return
	 */
	private static boolean placeController(PetriNet pn){
		for(Place p : pn.getPlaces()){
			if(p.getToken()<0)
				return false;
		}
		return true;
	}
	
	

}
