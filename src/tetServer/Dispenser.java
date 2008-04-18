/**
 * 
 */
package tetServer;

import java.rmi.RemoteException;

import tetPns.PetriNet;

/**
 * @author michele
 *
 */
public class Dispenser implements IDispenser {

	/* (non-Javadoc)
	 * @see tetServer.IDispenser#getNet(java.lang.String)
	 */
	public PetriNet getNet(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tetServer.IDispenser#list()
	 */
	public String list() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tetServer.IDispenser#sendNet(java.lang.String)
	 */
	public boolean sendNet(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

}
