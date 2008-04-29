/**
 * 
 */
package tetServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;

import tetPns.PetriNet;

/**
 * @author michele
 *
 */
public class Dispenser implements IDispenser, Serializable {

	final static private String EXT = ".tetpns";
	final static private String REPOSITORY = "repository";
	
	private File repository;
	private String [] a;
	
	public Dispenser(){
		repository = new File(REPOSITORY);
		if(!repository.exists())
			repository.mkdir();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Restituisce la rete di Petri voluta dal client
	 */
	public PetriNet getNet(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Presenta la lista di tutte le reti di Petri
	 * presenti nell'archivio
	 */
	public String[] list() throws RemoteException {
		repository = new File(REPOSITORY);
	
		return repository.list();
	}

	
	/**
	 * Salva la rete di Petri come oggetto serializzato nell'archivio
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public boolean sendNet(PetriNet pn, String name) throws RemoteException {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		try{
			
			File f1 = new File(REPOSITORY + File.separator + name + EXT);
			
			while(f1.exists()){
				System.out.println("Il file esiste già!!! Si desidera sovrascriverlo? (y/n)");
				if(input.readLine().equalsIgnoreCase("n")){
					System.out.println("Inserire il nuovo nome: ");
					f1 = new File(REPOSITORY + File.separator + input.readLine() + EXT);
				}
				else break;
			}
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f1));
			oos.writeObject(pn);
		}
		catch(Exception e){
			System.out.println("File error: " + e.toString());
		}
		
		return false;
	}

}
