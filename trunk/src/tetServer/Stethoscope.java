package tetServer;

public class Stethoscope extends Thread{

	final static int SLEEPTIME = 6000; // 60 secondi 
	
	private ClientMonitor cm=null;
	
	public Stethoscope(ClientMonitor clm){
		cm = clm;
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(SLEEPTIME);
				cm.clientGarbage();
			}
		} catch (Exception e ) {
			System.out.println("\nImpossibile controllare client attivi, si consiglia di riavviare il server.\n");
		}
			
	}

}
