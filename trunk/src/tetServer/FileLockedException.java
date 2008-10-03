package tetServer;

import java.rmi.RemoteException;

/**
 * Questa classe rappresenta l'eccezione che viene scatenata nel caso in cui il file desiderato dal
 * client sia già in uso da un altro client.
 * 
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */

public class FileLockedException extends RemoteException{
	public FileLockedException(){
		super("\nFile in uso da un altro client. Impossibile utilizzarlo.\n");
	}
	
	public String toString(){
		return getMessage() + ": FILE GIA' IN USO";
	}
}
