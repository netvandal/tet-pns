/**
 * 
 */
package tetServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Vector;

import tetPns.PetriNet;

/**
 * @author michele
 *
 */
public class Dispenser implements IDispenser, Serializable {

	private static final long serialVersionUID = -4799925758795034740L;
	private static final String EXT = ".tetpns";
	private static final String REPOSITORY = "repository";
	
	
	private File repository;
	
	public Dispenser(){
		repository = new File(REPOSITORY);
		if(!repository.exists())
			repository.mkdir();
	}
	
		
	/**
	 * Restituisce la rete di Petri voluta dal client
	 */
	public PetriNet getNet(String name) throws RemoteException {
		
		if(!checkFileExtension(name)){
			name = name + EXT;
		}
		
		File [] fileInRepository = repository.listFiles();
		try{
			for(File f : fileInRepository){
				if(f.getName().equalsIgnoreCase(name)){
					ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
					return (PetriNet) ois.readObject();
				}
			}
			return null; //Non esiste alcuna rete con quel nome
		}
		catch(Exception e){
			System.out.println("File error: " + e.toString());
			return null;
		}
	}

	
	/**
	 * Presenta la lista di tutte le reti di Petri
	 * presenti nell'archivio
	 */
	public String[] list() throws RemoteException {
		
		File[] fileInRepository = repository.listFiles();
		Vector<String> fileName = new Vector<String>();
		for(File f : fileInRepository){
			if(!f.isDirectory()){
				fileName.addElement(f.getName());
			}
		}
		
		return fileName.toArray(new String[fileName.size()]);
	}

	
	/**
	 * Salva la rete di Petri come oggetto serializzato nell'archivio
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
			return true;
		}
		catch(Exception e){
			System.out.println("File error: " + e.toString());
			return false;
		}
	}
	
	/**
	 * Controlla se fileName contiene l'estensione
	 */
	private boolean checkFileExtension(String fileName){
		String fileExtension;
		int lastPointIndex = fileName.lastIndexOf(".");
		if(lastPointIndex==-1)
			return false;
			
		fileExtension = fileName.substring(lastPointIndex);
		
		return fileExtension.equalsIgnoreCase(EXT);
	}

}
