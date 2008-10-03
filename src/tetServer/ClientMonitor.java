package tetServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ClientMonitor{ 
	
	final static private int DEATHTHRESHOLD=300000; //5 minuti
	
	private HashMap<Integer, String> fileLockList = new HashMap<Integer, String>();
	private HashMap<Integer, Long> liveClientList = new HashMap<Integer, Long>();
	
	public ClientMonitor() {
		
	}
	
	public void clientGarbage() {
		Set set = liveClientList.entrySet();
		Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if((Long.parseLong(me.getValue().toString())-System.nanoTime())>DEATHTHRESHOLD) {
	    	  removeFileLock(Integer.parseInt((me.getKey().toString())));
	    	  liveClientList.remove(me.getKey());
	    	  
	      }
	    }
	}
	
	public void removeFileLock(int id) {
		Set set = fileLockList.entrySet();
		Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if(Integer.parseInt((me.getKey().toString()))==id) {
	    	  fileLockList.remove(me.getKey());	  
	      }
	    }		
	}
	
	public boolean addFileLock(int id, String fileName) {
		Set set = fileLockList.entrySet();
		Iterator i = set.iterator();
		
		if(!i.hasNext()){
			System.out.println("Lock file: "+ fileName);
			fileLockList.put(id, fileName);
			return true;
		}
		
	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if(me.getValue().toString().equals(fileName)) return false; 
	    } 
		return true;	
	}
	
	public boolean addClient(int id) {
		liveClientList.put(id, System.nanoTime());
		return true;
	}
	
	public boolean getClient(int id) {
		if(liveClientList.get(id)!=null)
			return true;
		return false;
	}

	public boolean checkLock(String fileName) {
		Set set = fileLockList.entrySet();
		Iterator i = set.iterator();

	    while(i.hasNext()){
	      Map.Entry me = (Map.Entry)i.next();
	      if(me.getValue().toString().equals(fileName)) return true;
	    } 
		return false;
	}
	
}
