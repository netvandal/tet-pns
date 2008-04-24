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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see tetServer.ISimulator#getTransiction()
	 */
	public Vector<Transition> getTransiction() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tetServer.ISimulator#nextStep(tetPns.Transition)
	 */
	public PetriNet nextStep(Transition t) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tetServer.ISimulator#setNet(tetPns.PetriNet)
	 */
	public boolean setNet(PetriNet net) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see tetServer.ISimulator#stopSimulation()
	 */
	public boolean stopSimulation() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
