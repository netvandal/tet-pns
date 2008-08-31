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
 * Interfaccia per la gestione del simulatore
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */

public interface ISimulator extends Remote {
	
	/**
	 * Imposta la rete di Petri per la simulazione
	 * @param net La rete da simulare
	 * @return true in assenza di problemi
	 * @return false in caso di problemi
	 * @throws RemoteException
	 */
	public boolean setNet(PetriNet net)	    throws RemoteException;
	
	/**
	 * Genera il passaggio alla marcatura successiva 
	 * @param transId Id della transizione da far scattare
	 * @return true in assenza di problemi
	 * @return false in caso di problemi
	 * @throws RemoteException
	 */
	public boolean nextStep(String transId)	    throws RemoteException;

	/**
	 * Ferma la simulazione
	 * @throws RemoteException
	 */
	public boolean stopSimulation()	    throws RemoteException;
	
	/**
	 * Ritorna un vettore contenente le transizioni selezionabili
	 * @return Vector delle transizioni selezionabili
	 * @throws RemoteException
	 */
	public Vector<Transition> getSelectableTransition() throws RemoteException;
	
	/**
	 * Ritorna la  marcatura corrente della rete di Petri
	 * @return PetriNet  marcatura corrente della rete di Petri
	 * @throws RemoteException
	 */
	public PetriNet getNet() throws RemoteException;
}
