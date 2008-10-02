/**
 * 
 */
package tetServer;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;

import tetPns.PetriNet;
import tetPns.Transition;

/**
 * Gestisce la simulazione remota
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */
public class Simulator implements ISimulator, Serializable {

	HashMap<Integer, PetriNet> pList =  null;
	ClientMonitor cm = null;
	
	public Simulator(ClientMonitor cmon) {
		pList=new HashMap<Integer, PetriNet>();
		cm = cmon;
	}

	public Vector<Transition> getSelectableTransition(int id) {
		PetriNet myNet = (PetriNet)pList.get(id);
		if(myNet!=null) {
			return myNet.getEnabledTransitionsWithHighestPriority();		
		} else return null; // non stata settata nessuna rete di petri.		
	}


	public boolean nextStep(String transId, int id) {
		PetriNet myNet = (PetriNet)pList.get(id);
		if(myNet!=null) return myNet.fireTransition(transId);
		else return false;
	}

	
	public PetriNet getNet(int id) {
		PetriNet myNet = (PetriNet)pList.get(id);
		return myNet;
	}

	public boolean setNet(PetriNet net,int id) throws RemoteException {
		pList.put(id, net);	
		if(pList.get(id)!=null) return true;
			else return false;
	}


	public boolean stopSimulation(int id) throws RemoteException {
		
		pList.remove(id);
		cm.removeFileLock(id);
		System.gc();
		return true;
	}

	
	public boolean addClient(int id) throws RemoteException {
		//if(cm==null)System.out.println("Che palle!!!");
		if(!cm.getClient(id)) {
			cm.addClient(id);
			//System.out.println("Aggiungo heartbeat per il client " + id + " " + System.nanoTime() +"\n ");
			return true;
		} 
		return false;
	} 
	
	
	public boolean imAlive(int id) throws RemoteException {
		cm.addClient(id);
		//System.out.println("Aggiornato heartbeat per il client " + id + " " + System.nanoTime() +"\n ");
		return true;
	}

}
