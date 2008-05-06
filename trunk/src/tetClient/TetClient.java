package tetClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import parser.Parser;
import tetPns.PetriNet;
import tetPns.Transition;
import tetServer.IDispenser;
import tetServer.ISimulator;


public class TetClient {
	
	private ISimulator sim;
	private IDispenser disp;
	private PetriNet pn;
	
	private static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	private TetClient() {
		try{
			Registry reg = LocateRegistry.getRegistry();
			sim = (ISimulator) reg.lookup("SIMULATOR");
			disp = (IDispenser) reg.lookup("DISPENSER");
		}
		catch(RemoteException e){
			//Problemi di connessione
			System.out.println("Problemi di connessione");
			e.printStackTrace();
		}
		catch(NotBoundException e){
			System.out.println("I nomi dei servizi non hanno un bind nel registro.");
			e.printStackTrace();
		}
	}
	
	private void loadNet() throws IOException{
		
		
		do{
			System.out.println("Da dove vuoi caricare il file (1 - da casa/2 - da remoto/): ");
			switch(Integer.parseInt(input.readLine())){
			case 1: System.out.println("Inserisci il nome del file: ");
					File f = new File(input.readLine());
					if(f.exists()){
						Parser myParser = new Parser();
						pn = myParser.parsePetriNet(f.getName());

					} 
					else {
						System.out.println("Il file non esiste!!!");
					}
					break;
			case 2: String [] file = disp.list();
					for(String a : file){
						System.out.println(a);
					}
					System.out.println("Scegli quale rete caricare:");
					pn = disp.getNet(input.readLine());
					break;
			
			default:break;
			}
		}while(pn==null);
		sim.setNet(pn);
		pn.getInfo();

	}
	
	private void saveNet(){
		try{
			System.out.println("Inserisci il nome del file:");
			disp.sendNet(pn, input.readLine());
		}
		catch(Exception e){
			System.out.println("Problemi in saveNet");
			e.printStackTrace();
		}
	}
	
	private void oneStepBeyond(){
		try{
			Vector<Transition> q = sim.getSelectableTransition();
			for(Transition t : q)
				t.getInfo();
		
			System.out.println("\n\nCosa vuoi far scattare?");
			String id = null;
			
			System.out.println("\t\tSCATTA " + (id = input.readLine()));
			if(sim.nextStep(id))
				pn = sim.getNet();
			else  System.out.println("\n\nErrore nel far scattare la transizione");
		}
		catch(RemoteException e){
			System.out.println("Problemi di connessione");
			e.printStackTrace();
		}
		catch(IOException e){
			System.out.println("Problemi con IO");
			e.printStackTrace();
		}
		
		pn.getInfo();
	}
	
	private void stopClient(){
		try{
			sim.stopSimulation();
		}
		catch(RemoteException e){
			System.out.println("Problemi di connessione");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		
		System.setSecurityManager(new RMISecurityManager());
		
		try {
            TetClient tc = new TetClient();
            tc.loadNet();
            
            
            System.out.println("Inizio simulazione!!!");
            
            do{
            	tc.oneStepBeyond();

            	System.out.println("Vuoi Continuare? (y/n)");
            	if(input.readLine().equalsIgnoreCase("n"))
            		break;
            	
            }while(true);
            
            System.out.println("Vuoi salvare la rete?(y/n)");
            
            if(input.readLine().equalsIgnoreCase("y")){
            	tc.saveNet();
            }
            
            tc.stopClient();
            
        } catch (Exception e) {
            // Something wrong here
            e.printStackTrace();
        }
		
	}
	
}
