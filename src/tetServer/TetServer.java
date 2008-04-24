/**
 * 
 */
package tetServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author michele
 *
 */
public class TetServer extends UnicastRemoteObject{

	protected TetServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 * @throws AlreadyBoundException 
	 */
	public static void main(String[] args) throws AlreadyBoundException  {
		try {
			
			Dispenser disp = new Dispenser();
			Simulator sim = new Simulator();
			
			//Naming.rebind("SIMULATOR", sim);
			//Naming.rebind("DISPENSER", disp);
		
			Registry r = LocateRegistry.getRegistry();
			r.rebind("SIMULATOR", sim);
			r.rebind("DISPENSER", disp);
		}
		/* If any communication failures occur... */
		catch (RemoteException e) {
			System.out.println("Communication error " + e.toString());
		}
	}

}
