/**
 * 
 */
package tetServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import tetPns.PetriNet;
import tetPns.Transition;

/**
 * @author michele
 *
 */
public interface ISimulator extends Remote {
	

	public boolean setNet(PetriNet net)	    throws RemoteException;
	
	
	public boolean nextStep(String transId)	    throws RemoteException;

	
	public boolean stopSimulation()	    throws RemoteException;
	

	public Vector<Transition> getSelectableTransition() throws RemoteException;
	
	public PetriNet getNet() throws RemoteException;
}
