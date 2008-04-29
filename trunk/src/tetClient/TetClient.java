package tetClient;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import tetServer.IDispenser;


public class TetClient {
	public static void main(String[] args) {
		
		System.setSecurityManager(new RMISecurityManager());
		
		try {
            Registry reg = LocateRegistry.getRegistry();
            //Thread.sleep(10000);
            IDispenser d = (IDispenser) reg.lookup("DISPENSER");
            //Thread.sleep(7000);
            System.out.println(d.getNet("Ciao"));
            
        } catch (Exception e) {
            // Something wrong here
            e.printStackTrace();
        }
		
	}
	
}
