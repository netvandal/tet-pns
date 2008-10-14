package tetServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ClientMonitor{ 
	
	final static private long DEATH_THRESHOLD=60000; //1 minuto
	
	//Lista dei file bloccati
	private ConcurrentHashMap<Integer, String> fileLockList = null;
	
	//Lista dei client ancora "vivi"
	private ConcurrentHashMap<Integer, Long> liveClientList = null;
	
	/**
	 * Costruttore della classe ClientMonitor
	 */
	public ClientMonitor() {
		fileLockList = new ConcurrentHashMap<Integer, String>();
		liveClientList = new ConcurrentHashMap<Integer, Long>();
	}
	
	/**
	 * Controlla che l'ultimo heartbeat del client sia arrivato entro il
	 * DEATH_THRESHOLD. Se questo non avviene il client viene eliminato
	 * dal liveClientList
	 */
	public synchronized void clientGarbage() {
		Set set = liveClientList.entrySet();
		Iterator i = set.iterator();
		long lastBeatTime;
		
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      lastBeatTime=(System.nanoTime()-Long.parseLong(me.getValue().toString()))/1000000;
	      //System.out.println("Client: " + me.getKey().toString() + " Tempo di risposta:" + lastBeatTime);
	      if(lastBeatTime>DEATH_THRESHOLD) {
	    	  //System.out.println("Il client " + me.getKey() + " è morto!!!");
	    	  removeFileLock(Integer.parseInt((me.getKey().toString())));
	    	  //liveClientList.remove(me.getKey());
	    	  i.remove();
	    	  
	      }
	    }
	}
	
	/**
	 * Elimina il file bloccato dal client specificato da id dalla
	 * lista dei file bloccati. Il file sarà quindi disponibile
	 * ad altri client.
	 * @param id
	 * @return true se la rimozione va a buon fine, false in caso di errori
	 */
	public synchronized boolean removeFileLock(int id) {
		if(fileLockList.containsKey(id)){
			fileLockList.remove(id);
			return true;
		}
		return false;
	}
	
	/**
	 * Aggiunge il file specificato da fileName alla lista dei file bloccati.
	 * @param id
	 * @param fileName
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public synchronized boolean addFileLock(int id, String fileName) {
		try{
			
			//System.out.println("Lock di " + fileName + "   " +  id);
			if(!fileLockList.containsValue(fileName)) {
				fileLockList.put(id, fileName);
				return true;
			} else return false;
				
		}catch(NullPointerException e){
			return false;
		}
	}
	
	/**
	 * Aggiunge un client alla lista dei client vivi
	 * @param id
	 * @return true se l'operazione va a buon fine, false altrimenti
	 */
	public synchronized boolean addClient(int id) {
		liveClientList.put(id, System.nanoTime());
		//System.out.println("Aggiornato heartbeat per il client " + id + " " + System.nanoTime());
		return true;
	}
	
	/**
	 * Elimina un client dalla lista dei client vivi
	 * @param id
	 */
	public synchronized boolean removeClient(int id){
		try{
			liveClientList.remove(id);
			return true;
		}catch(NullPointerException e){
			return false;
		}
	}
	
	/**
	 * Controlla se la lista dei client vivi contiene il client
	 * specificato
	 * @param id
	 * @return true se il client è vivo, false altrimenti
	 */
	public synchronized boolean getClient(int id) {
		return liveClientList.containsKey(id);
	}

	/**
	 * Controlla se il file specificato con fileName è presente nella
	 * lista dei file bloccati.
	 * @param fileName
	 * @return true se il file è bloccato, false altrimenti
	 */
	public synchronized boolean checkLock(String fileName) {
		return fileLockList.containsValue(fileName);
	}
	
}
