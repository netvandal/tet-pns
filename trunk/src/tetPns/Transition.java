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
	private static final int MAXPRIORITY = 10;
	
	int priority = MAXPRIORITY;
	Flow myFlow = null;
	
	/**
	 * Constructor
	 * @param p Priority of the transition
	 */
	public Transition(int p) {
		
	}
	
	public boolean isActive() {
		return true;
	}
	
	public void fire() {
		
	}
	
	public void setPriority(int p) {
		if(p>MAXPRIORITY) p = 10;
		this.priority = p;
	}
	
	public int getPriority() {
		return this.priority;
	}
}
