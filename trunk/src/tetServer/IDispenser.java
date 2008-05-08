/**
 * 
 */
package tetServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import tetPns.PetriNet;

/**
 * @author michele
 *
 */
public interface IDispenser extends Remote {

	
	public String[] list() throws RemoteException;

	
	public boolean sendNet(PetriNet pn, String name) throws RemoteException;

	
	public PetriNet getNet(String nome) throws RemoteException;

	
}
