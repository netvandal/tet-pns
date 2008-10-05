package tetClient;

import tetServer.ISimulator;

public class ClientHeart extends Thread  {

	final static int SLEEPTIME =  2000; // 2 secondi
	
	ISimulator sim = null;
	int id;

	
	public ClientHeart(ISimulator s, int idClient) {
		sim = s;
		id = idClient;
	}
	
	public void run() {
		try {
			while(true) {
				sim.imAlive(id);
				Thread.sleep(SLEEPTIME);
			}
		} catch ( Exception e) {
			 System.out.println("\n\nErrore di comunicazione con il server.\n Termine del programma.");
			 System.exit(1);
		}
	}
	
}
