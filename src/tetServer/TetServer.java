/**
 * 
 */
package tetServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * @author Alessio, Michele
 *
 */
public class TetServer extends UnicastRemoteObject{


	protected TetServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)  {
		try {
			
			Dispenser disp = new Dispenser();
			Simulator sim = new Simulator();
			
		
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
