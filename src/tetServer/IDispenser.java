/**
 * 
 */
package tetServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import tetPns.PetriNet;

/**
 * Interfaccia per la gestione dell'archivio remoto
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */
public interface IDispenser extends Remote {

	/**
	 * Restituisce la lista delle reti disponibili
	 * @return String lista delle reti di petri disponibili
	 */
	public String[] list() throws RemoteException;

	/**
	 * Salva un oggetto PetriNet serializzato nel repository
	 * @param pn Rete di Petri
	 * @param name Nome desiderato per il salvataggio
	 * @return true in caso di successo
	 * @return false in caso il file salvato esista gi�
	 * @throws RemoteException
	 */
	public int sendNet(PetriNet pn, String name, boolean overWrite) throws RemoteException;

	/**
	 * Richiede una rete di petri
	 * @param nome Nome della rete di Petri da Caricare
	 * @return PetriNet la rete di Petri desiderata
	 * @throws RemoteException, FileLockedException
	 */
	public PetriNet getNet(String nome, int id) throws RemoteException;
	
	/**
	 * Rimuove il lock sui file
	 * @param id Id del client
	 */
	public void removeLock(int id) throws RemoteException;
	
}
