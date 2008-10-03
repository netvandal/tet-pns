package tetServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ClientMonitor{ 
	
	final static private long DEATH_THRESHOLD=300000; //5 minuti
	final static private long DEATH_THRESHOLD_DEBUG=15000; //15 secondi
	
	private HashMap<Integer, String> fileLockList = null;
	private HashMap<Integer, Long> liveClientList = null;
	
	public ClientMonitor() {
		fileLockList = new HashMap<Integer, String>();
		liveClientList = new HashMap<Integer, Long>();
	}
	
	public synchronized void clientGarbage() {
		Set set = liveClientList.entrySet();
		Iterator i = set.iterator();
		long lastBeatTime;
		
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      lastBeatTime=(System.nanoTime()-Long.parseLong(me.getValue().toString()))/1000000;
	      //System.out.println("Client: " + me.getKey().toString() + " Tempo di risposta:" + lastBeatTime);
	      if(lastBeatTime>DEATH_THRESHOLD_DEBUG) {
	    	  //System.out.println("Il client " + me.getKey() + " è morto!!!");
	    	  removeFileLock(Integer.parseInt((me.getKey().toString())));
	    	  //liveClientList.remove(me.getKey());
	    	  i.remove();
	    	  
	      }
	    }
	}
	
	public synchronized void removeFileLock(int id) {
		Set set = fileLockList.entrySet();
		Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if(Integer.parseInt((me.getKey().toString()))==id) {
	    	  //fileLockList.remove(me.getKey());
	    	  i.remove();
	      }
	    }		
	}
	
	public synchronized boolean addFileLock(int id, String fileName) {
		Set set = fileLockList.entrySet();
		Iterator i = set.iterator();
		
		if(!i.hasNext()){
			//System.out.println("Lock file: "+ fileName);
			fileLockList.put(id, fileName);
			return true;
		}
		
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if(me.getValue().toString().equals(fileName)){
	    	  fileLockList.put(id,fileName);
	    	  return true;
	      }   
	    } 
		return false;	
	}
	
	public synchronized boolean addClient(int id) {
		liveClientList.put(id, System.nanoTime());
		//System.out.println("Aggiornato heartbeat per il client " + id + " " + System.nanoTime());
		return true;
	}
	
	public synchronized void removeClient(int id){
		liveClientList.remove(id);
	}
	
	public synchronized boolean getClient(int id) {
		if(liveClientList.get(id)!=null)
			return true;
		return false;
	}

	public synchronized boolean checkLock(String fileName) {
		Set set = fileLockList.entrySet();
		Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if(me.getValue().toString().equals(fileName)) return true;
	    } 
		return false;
	}
	
}
