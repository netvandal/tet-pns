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
			
			IDispenser disp = new Dispenser();
			ISimulator sim = new Simulator();
			
			IDispenser dispStub  = (IDispenser) UnicastRemoteObject.exportObject(disp,0);
			ISimulator simStub  = (ISimulator) UnicastRemoteObject.exportObject(sim,0);
			
			Registry r = LocateRegistry.getRegistry();
			r.rebind("SIMULATOR", simStub);
			r.rebind("DISPENSER", dispStub);
			System.out.println("Server avviato correttamente... buona simulazione ;)");
			System.out.println("Pubblicazione dei servizi eseguita con successo.\n" +
					"E' ora possibile lanciare il programma client.\n" +
					"BUONA SIMULAZIONE!!!");
			
		}
		// se c'è qualche errore di comunicazione
		catch (RemoteException e) {
			System.out.println("Errore di comunicazione " + e.toString());
		}
	}

}
