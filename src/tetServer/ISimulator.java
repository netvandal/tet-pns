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
	public boolean setNet(PetriNet net, int id)	    throws RemoteException;
	
	/**
	 * Genera il passaggio alla marcatura successiva 
	 * @param transId Id della transizione da far scattare
	 * @return true in assenza di problemi
	 * @return false in caso di problemi
	 * @throws RemoteException
	 */
	public boolean nextStep(String transId, int id)	    throws RemoteException;

	/**
	 * Ferma la simulazione
	 * @throws RemoteException
	 */
	public boolean resetSimulator(int id)	    throws RemoteException;
	
	/**
	 * Ritorna un vettore contenente le transizioni selezionabili
	 * @return Vector delle transizioni selezionabili
	 * @throws RemoteException
	 */
	public Vector<Transition> getSelectableTransition(int id) throws RemoteException;
	
	/**
	 * Ritorna la  marcatura corrente della rete di Petri
	 * @return PetriNet  marcatura corrente della rete di Petri
	 * @throws RemoteException
	 */
	public PetriNet getNet(int id) throws RemoteException;
	
	
	/**
	 * Aggiunge un clientId alla lista dei client attivi
	 * @param id
	 * @return true se l'operazioine è andata a buon fine, false altrimenti
	 * @throws RemoteException
	 */
	public boolean addClient(int id) throws RemoteException;
	
	/**
	 * Elimina un client dalla lista dei client vivi
	 * @param id
	 * @return true se l'operazione è andata a buon fine, false altrimenti
	 * @throws RemoteException
	 */
	public boolean deleteClient(int id) throws RemoteException;
	
	/**
	 * Riceve l'heartbeat dal client 
	 */
	public boolean imAlive(int id) throws RemoteException;
}
