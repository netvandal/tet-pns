/**
 * 
 */
package tetServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

import tetPns.PetriNet;
import tetPns.Transition;

/**
 * Gestisce la simulazione remota
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */
public class Simulator implements ISimulator, Serializable {

	PetriNet myNet = null;


	public Vector<Transition> getSelectableTransition() {
		if(myNet!=null) {
			return myNet.getEnabledTransitionsWithHighestPriority();		
		} else return null; // non stata settata nessuna rete di petri.		
	}


	public boolean nextStep(String transId) {
		if(myNet!=null) return myNet.fireTransition(transId);
		else return false;
	}

	
	public PetriNet getNet() {
		return myNet;
	}

	public boolean setNet(PetriNet net) throws RemoteException {
		myNet = net;		
		if(myNet!=null) return true;
			else return false;
	}


	public boolean stopSimulation() throws RemoteException {
		myNet = null;
		System.gc();
		return true;
	}

}
