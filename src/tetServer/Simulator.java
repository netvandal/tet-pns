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
 * @author michele
 *
 */
public class Simulator implements ISimulator, Serializable {

	PetriNet myNet = null;

	private static final long serialVersionUID = 1L;

	public Vector<Transition> getSelectableTransition() {
		if(myNet!=null) {
			return myNet.getEnabledTransitionsWithHighestPriority();		
		} else return null; // non stata settata nessuna rete di petri.		
	}

	/* 
	 * fa scattare la transizione selezionata
	 */
	public boolean nextStep(String transId) {
		if(myNet!=null) return myNet.fireTransition(transId);
		else return false;
	}

	
	public PetriNet getNet() {
		return myNet;
	}
	
	/*
	 *  Setta la rete di petri di cui effettuare la
	 * simulazione. 
	 */
	public boolean setNet(PetriNet net) throws RemoteException {
		myNet = net;
		return true;
		
		// TODO verificare se riusciuto il settaggio, in caso contrario ritorno false.
	}


	public boolean stopSimulation() throws RemoteException {
		myNet = null;
		return true;
	}

}
