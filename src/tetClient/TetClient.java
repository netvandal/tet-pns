package tetClient;

import java.io.File;
import java.io.IOException;
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
	
	private final static String[] LOAD_NET_OPTION = {"Carica File Locale","Caricamento File Remoto","Torna al Menu Principale"};
	private final static String[] MAIN_MENU_OPTION = {"Carica Rete","Avvia Simulazione","Salva Marcatura Corrente","Modifica Priorità","Esci"};
	
	private Menu mainMenu,loadNetMenu,repositoryMenu,simMenu;
	private ISimulator sim;
	private IDispenser disp;
	private PetriNet pn;
	private GraphEditorTester graph;
	
	private TetClient() {
		try{
			loadNetMenu = new Menu("Caricamento Rete",LOAD_NET_OPTION);
			pn=null;
			
			Registry reg = LocateRegistry.getRegistry();
			sim = (ISimulator) reg.lookup("SIMULATOR");
			disp = (IDispenser) reg.lookup("DISPENSER");
		}
		catch(RemoteException e){
			//Problemi di connessione
			System.out.println("Problemi di connessione");
			e.printStackTrace();
			System.exit(1);
		}
		catch(NotBoundException e){
			System.out.println("I nomi dei servizi non hanno un bind nel registro.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void loadNet() throws IOException{
		boolean continua=true;
		do{
			switch(loadNetMenu.scelta()){
				case 1: File f = new File(Service.leggiStringa("Inserisci il nome del file: "));
					if(f.exists()){
						Parser myParser = new Parser();
						pn = myParser.parsePetriNet(f.getName());
						if(pn==null)
							System.out.println("\t\tAttenzione!!! Il file non è valido.");
					} 
					else {
						System.out.println("\t\tIl file non esiste!!!");
					}break;
				case 2: String[] file = disp.list();
					if(file.length!=0){
						repositoryMenu = new Menu("Repository",file);
						pn = disp.getNet(file[repositoryMenu.scelta()-1]);
					}
					else
						System.out.println("Non ci sono reti nel Repository");
					break;
				case 3:continua=false;break;
					
				default:System.out.println("Opzione non presente");break;
			}
		}while(pn==null && continua);
		
		if(pn!=null){
			pn.getInfo();
			sim.setNet(pn);
		}
	}
	
	private void saveNet(){
		try{
			disp.sendNet(pn, Service.leggiStringa("Inserisci il nome del file:"));
		}
		catch(Exception e){
			System.out.println("Problemi in saveNet");
			e.printStackTrace();
		}
	}
	
	private void changeTransitioPriority(){
		int newPriority;
		Transition t;
		if(pn==null){
			System.out.println("\nATTENZIONE!!! Non è stata caricata alcuna rete di Petri.");
			return;
		}
		Vector<Transition> transitions = pn.getTransitions();
		for(int i=0;i<transitions.size();i++){
			t=transitions.get(i);
			if((newPriority=Service.leggiInt("Inserire la nuova priorità per " + t.getId() + ":"))<0){
				System.out.println("\nATTENZIONE!!! La priorità deve essere positiva.");
				i--;
			}
			else
				t.setPriority(newPriority);
		}
			
		try {
			sim.setNet(pn);
		} catch (RemoteException e) {
			System.out.println("Problemi con il server");
		}
	}
	
	private boolean oneStepBeyond(){
		try{
			Vector<Transition> trans = sim.getSelectableTransition();
			if(trans==null){
				System.out.println("\tDEADLOCK DEADLOCK DEADLOCK DEADLOCK" +
						"\n\tNon ci sono transizioni abilitate\n");
				return false;
			}
			
			String[] transitionId = new String[trans.size()];
			
			int i=0;
			for(Transition t : trans){
				transitionId[i] = t.getId();
				i++;
			}
			
			int transitionChoice;
			if(transitionId.length!=1){
				simMenu = new Menu("Transizioni Abilitate",transitionId);
				transitionChoice=simMenu.scelta()-1;
			}
			else transitionChoice=0; //Viene selezionata automaticamente l'unica transizione abilitata
				
			System.out.println("\t\tSCATTA " + transitionId[transitionChoice]);
			
			if(sim.nextStep(transitionId[transitionChoice])) {
				pn = sim.getNet();
				this.graph.redraw();
			}
			else{
				System.out.println("\n\nErrore nello scatto della transizione" + transitionId[transitionChoice]);
				return false;
			}
		}
		catch(RemoteException e){
			System.out.println("Problemi di connessione");
			e.printStackTrace();
		}
		
		pn.getInfo();
		return true;
	}
	
	private void manageSimulation(){
		try{
			if(sim.getNet()==null){
				System.out.println("\nATTENZIONE!!! Non è stata caricata alcuna rete di Petri.");
				return;
			}
		}
		catch(RemoteException e){
			System.out.println("Problemi di connessione");
			e.printStackTrace();
		}
		
		System.out.println("\n\n\t\tINIZIO SIMULAZIONE");
		this.graph = new GraphEditorTester(pn);
        
		boolean esci=false;
		
		/**
		 * GUARDA CHE BELLO!!!
		 * SFRUTTA LA VALUTAZIONE IN CORTOCIRCUITO
		 */
		while(!esci && oneStepBeyond())
			esci = !Service.risposta("\nContinuare la simulazione ");

		System.out.println("Fine della simulazione");
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
            boolean continua=true;
            tc.mainMenu = new Menu("Menu Principale",MAIN_MENU_OPTION);
            do{
            	switch(tc.mainMenu.scelta()){
            		case 1:tc.loadNet();break;
            		case 2:tc.manageSimulation();
            			
            		break;
	            	case 3:tc.saveNet();break;
	            	case 4:tc.changeTransitioPriority();break;
	            	case 5:tc.stopClient();
	            		continua=false;break;
	            	default:break;
            	}
            }while(continua);

        } catch (Exception e) {
            // Something wrong here
            e.printStackTrace();
        }
		
	}
	
}
