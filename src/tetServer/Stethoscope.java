package tetServer;

public class Stethoscope extends Thread{

	final static int SLEEPTIME = 6000; // 60 secondi 
	final static int SLEEPTIME_DEBUG = 20000; // 20 secondi 
	
	private ClientMonitor cm=null;
	
	public Stethoscope(ClientMonitor clm){
		cm = clm;
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(SLEEPTIME_DEBUG);
				System.out.println("\t\tCLIENT GARBAGE\n");
				cm.clientGarbage();
			}
		} catch (Exception e ) {
			System.out.println("\nImpossibile controllare client attivi, si consiglia di riavviare il server.\n");
		}
			
	}

}
