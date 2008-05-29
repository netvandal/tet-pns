package parser;

import java.util.Vector;

import tetPns.*;


/**
 * Validator si occupa di validare la rete di Petri
 * appena immessa. Controlla alcune caratteristiche
 * utili per una buona simulazione.
 * 
 * @author Alessio
 */
public class Validator {
	
	private PetriNet pn;
	
	public Validator(){
		pn=new PetriNet();
	}
	
	public void validate() throws InvalidFileException{
		if(!netIdController() || !transitionInOutController() || !petriNetController())
			throw new InvalidFileException();
	}
	
	/**
	 * Controlla che la rete abbia un numero di archi sensato
	 * (numeroArchi <= 2* numero_Posti * numero_Transizioni)
	 */
	private boolean petriNetController(){
		
		int numberOfPlace=pn.getPlaces().size();
		int numberOfTransition=pn.getTransitions().size();
		int numberMaxOfArc=2*numberOfPlace*numberOfTransition;
		return (pn.getArcs().size()<=numberMaxOfArc);	
	}
	
	/**
	 * Controlla che tutte le transizioni abbiano almeno un arco in ingresso
	 * e uno in uscita.
	 */
	private boolean transitionInOutController(){
		Vector<Transition> trans=pn.getTransitions();
		
		for(Transition t : trans){
			if(t.getArcIn().size()==0 || t.getArcOut().size()==0)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Controlla che non ci siano id duplicati all'interno
	 * della rete
	 */
	private boolean netIdController(){
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
	 * Controlla l'esistenza e la validità di tutti gli elementi
	 * di un place
	 * @throws InvalidFileException 
	 */
	public void placeController(String elementId, String nToken,
			String posX, String posY, String labelX, String labelY) throws InvalidFileException {
		
		Place p=null;
		int token=0;
		try{
			p=new Place(); //la quantità di token di default è 0
			if(elementId==null)
				throw new InvalidFileException();
			
			p.setId(elementId);
			System.out.println(elementId);
			p.setCoordX(Math.round(Float.parseFloat(posX)));
			//System.out.println("1");
			p.setCoordY(Math.round(Float.parseFloat(posY)));
			//System.out.println("2");
			p.setLabelX(Math.round(Float.parseFloat(labelX))); // FIXME da controllare... un label..perch lo parso
			//System.out.println("3");
			p.setLabelY(Math.round(Float.parseFloat(labelY)));
			System.out.println(nToken);
			if((token = Integer.parseInt(nToken))>=0)
				p.addToken(token);
				
		}
		catch(java.lang.NumberFormatException e){
			System.out.println("In place error");
			throw new InvalidFileException();
		}
		pn.addPlace(p);
	}
	
	/**
	 * Controlla l'esistenza e la validità di tutti gli elementi
	 * che compongono una transizione
	 * 
	 * @throws InvalidFileException
	 */
	public void transitionController(String elementId, String nPriority,
			String posX, String posY, String labelX, String labelY) throws InvalidFileException{
		
		Transition t=null;

		try{
			if(nPriority==null || Integer.parseInt(nPriority)<=0)
				t=new Transition(0);	//Priorità di default
			else
				t=new Transition(Integer.parseInt(nPriority));
			
			if(elementId==null)
				throw new InvalidFileException();
			
			t.setId(elementId);
			t.setCoordX(Math.round(Float.parseFloat(posX)));
			t.setCoordY(Math.round(Float.parseFloat(posY)));
			t.setLabelX(Math.round(Float.parseFloat(labelX)));
			t.setLabelY(Math.round(Float.parseFloat(labelY)));
		}
		catch(java.lang.NumberFormatException e){
			throw new InvalidFileException();
		}
		pn.addTransition(t);
	}
	
	/**
	 * Controlla l'esistenza e la validità di tutti gli elementi
	 * che compongono un arco
	 * 
	 * @throws InvalidFileException
	 */
	public void arcController(String elementId, String source, String target) throws InvalidFileException{

		if(elementId==null || source==null || target==null)
			throw new InvalidFileException();
		
		Element s,t;
		
		s=pn.getElementById(source);
		t=pn.getElementById(target);
		
		//Ogni arco deve essere collegato a 2 elementi
		if(s==null || t== null)					
			throw new InvalidFileException();
		
		//Se source è un posto allora target deve essere una transizione
		if(s instanceof Place && !(t instanceof Transition))
			throw new InvalidFileException();
		
		//Se source è una transizione allora target deve essere un posto
		if(s instanceof Transition && !(t instanceof Place))
			throw new InvalidFileException();
		
		pn.addArc(new Arc(elementId,s,t));
	}
	
	public PetriNet getPetriNet(){
		return pn;
	}

}
