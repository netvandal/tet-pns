/**
 * 
 */
package tetPns;

import java.util.Vector;

/**
 * Flow Descriptor
 * A Flow consist in the set of Arcs that connect transition and places.<br>
 * It's organized from the point of view of the transition so ArcsIn represent the Arcs that tarts from a place and enter in a transition,<br>
 * and ArcsOut the Arcs that exit from the transition and then enter in a place. 
 * connection between place
 * 
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Flow {
	private Vector arcIn;
	private Vector arcOut;
	
	public void addArcIn(Arc a) {
		this.arcIn.add(a);
	}
	
	public void addArcOut(Arc a) {
		this.arcOut.add(a);
	}
}
