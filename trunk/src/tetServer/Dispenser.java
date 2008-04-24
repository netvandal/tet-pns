/**
 * 
 */
package tetServer;

import java.io.Serializable;
import java.rmi.RemoteException;

import tetPns.PetriNet;

/**
 * @author michele
 *
 */
public class Dispenser implements IDispenser, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see tetServer.IDispenser#getNet(java.lang.String)
	 */
	public String /*PetriNet*/ getNet(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return nome;
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
