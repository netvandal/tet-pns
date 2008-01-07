package tetPns;

import java.util.Vector;

/**
 * Transition Descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Transition  extends Element {
	
	private int priority = 0;
	private String transId;
	private Vector<Arc> arcIn;
	private Vector<Arc> arcOut;
	
	
	/**
	 * Constructor
	 * @param p Priority of the transition
	 */
	public Transition(int p) {
		this.transId = null;
		arcIn = new Vector<Arc>();
		arcOut = new Vector<Arc>();
	}
	
	
	/**
	 * Verify if the transition can fire.
	 * @return boolean (true = can fire; false = can't fire)
	 */
	public boolean isEnabled() {
		Place pre;
		for(Arc a : arcIn){
			pre = (Place) a.getSourceElement();
			if(pre.getToken() < a.getWeight())
				return false;
		}
		return true;
	}
		
	public void fire() {
		Place p;
		
		//Remove token from pre(t)
		for(Arc a : arcIn){
			p = (Place) a.getSourceElement();
			p.subToken(a.getWeight());
		}
		
		//Add token to post(t)
		for(Arc a : arcOut){
			p = (Place) a.getTargetElement();
			p.addToken(a.getWeight());
		}
	}
	
	public void addArcIn(Arc a){
		arcIn.addElement(a);
	}
	
	public void addArcOut(Arc a){
		arcOut.addElement(a);
	}
	
	public void setPriority(int p) {
		this.priority = p;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public void setId(String id) {
		this.transId = id;
	}
	
	public void getInfo() {
		System.out.println("Transizione " + this.transId);
		
		System.out.println("Priorità " + this.priority);
		
		System.out.println("Archi Ingresso");
		for(Arc a : arcIn)a.getInfo();
		
		System.out.println("Archi Uscita");
		for(Arc a : arcOut)a.getInfo();
		
		System.out.println("\n");
	}

	public String getId() {
		return this.transId;
	}
}
