/**
 * 
 */
package tetPns;

/**
 * Transition Descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Transition  extends Element {
	
	private int priority;
	private Flow myFlow = null;
	private String transId;
	
	/**
	 * Constructor
	 * @param p Priority of the transition
	 */
	public Transition(int p) {
		this.transId = null;
	}
	
	public boolean isActive() {
		return true;
	}
	
	public void fire() {
		
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
	}

	public String getId() {
		return this.transId;
	}
}
