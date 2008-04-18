/**
 * 
 */
package tetServer;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * @author michele
 *
 */
public class TetServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			Dispenser disp = new Dispenser();
			Simulator sim = new Simulator();
			
			Naming.rebind("SIMULATOR", sim);
			Naming.rebind("DISPENSER", disp);
		
		}
		/* If the URL-formatted name is not legal... */
		catch (java.net.MalformedURLException e) {
			System.out.println("Malformed URL for TetServer name " + e.toString());
		}
		
		/* If any communication failures occur... */
		catch (RemoteException e) {
			System.out.println("Communication error " + e.toString());
		}
	}

}
