/**
 * 
 */
package tetServer;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Effettua il binding dei servizi nel registro RMI
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */

public class TetServer extends UnicastRemoteObject{


	protected TetServer() throws RemoteException {
		super();
	}

	public static void main(String[] args)  {
		try {
			
			Dispenser disp = new Dispenser();
			Simulator sim = new Simulator();
			
		
			Registry r = LocateRegistry.getRegistry();
			r.rebind("SIMULATOR", sim);
			r.rebind("DISPENSER", disp);
			
		}
		// se c'è qualche errore di comunicazione
		catch (RemoteException e) {
			System.out.println("Errore di comunicazione " + e.toString());
		}
	}

}
