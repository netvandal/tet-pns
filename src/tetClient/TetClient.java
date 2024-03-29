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

/**
 * Classe principale del Client.
 * Si occupa della gestione dell'interazione con l'utente e della gestione del workflow dell'applicazione.
 * Gestisce la comunicazione con il server necessaria alla simulazione ed eventualmente al caricamento delle
 * reti salvate in remoto.
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */


public class TetClient {
	

	
	// creazione variabili per il men�
	private final static String[] LOAD_NET_OPTION = {"Carica File Locale","Caricamento File Remoto","Torna al Menu Principale"};
	private final static String[] MAIN_MENU_OPTION = {"Carica Rete","Avvia Simulazione","Salva Marcatura Corrente","Modifica Priorità","Esci"};
	
	private Menu mainMenu,loadNetMenu,repositoryMenu,simMenu;
	
	// variabili necessarie alla simulazione
	private ISimulator sim;
	private IDispenser disp;
	private PetriNet pn;

	private boolean remoteFile = false;
	
	// variabile per la gestione della finestra grafica
	private GraphEditorTester graph; 
	// id del client
	int id;
	
	/**
	 * Costruttore.
	 * Setup della connessione con il server, inizializzazione variabili
	 */
	
	private TetClient() {
		
		// genero id univoco, si spera per il client
		id = Service.estraiIntero()+(int)System.nanoTime();
		
		try{
			loadNetMenu = new Menu("Caricamento Rete",LOAD_NET_OPTION);
			pn=null;
			
			// Localizzazione del registro RMI
			Registry reg = LocateRegistry.getRegistry();
			sim = (ISimulator) reg.lookup("SIMULATOR");
			disp = (IDispenser) reg.lookup("DISPENSER");
			
			int i = 0;
			for(i=0;i<5 && !sim.addClient(id);i++) id = Service.estraiIntero()+(int)System.nanoTime();
			if(i==5) {
				System.out.println("Impossibile ottenere un identificativo. \n Chiusura del programma.");
				System.exit(1);
			}
			
            ClientHeart beat = new ClientHeart(sim, id);
            beat.start();
			
		}
		catch(RemoteException e){
			//Problemi di connessione
			System.out.println("Problemi di connessione.\nChiusura del programma.");
			//e.printStackTrace();
			System.exit(1);
		}
		catch(NotBoundException e){
			System.out.println("I nomi dei servizi non hanno un bind nel registro.\nChiusura del programma.");
			//e.printStackTrace();
			System.exit(1);
		}
		
	
	}
	
	/**
	 * Gestione caricamento rete di Petri
	 * @throws IOException
	 */
	
	private void loadNet() throws IOException{
		boolean continua=true;
		boolean noMsg = false;
		sim.resetSimulator(id);

		do{
			switch(loadNetMenu.scelta()){
				case 1: File f = new File(Service.leggiStringa("Inserisci il nome del file: "));
					if(f.exists()){
						Parser myParser = new Parser();
						pn = myParser.parsePetriNet(f.getName());
						remoteFile=false;
					} 
					else {
						System.out.println("\t\tIl file non esiste!!!");
					}break;
				case 2: String[] file = disp.list();
					if(file.length!=0){
						repositoryMenu = new Menu("Repository",file);
					
						pn = disp.getNet(file[repositoryMenu.scelta()-1], id);
						remoteFile=true;
						if(pn==null )	System.out.println("Problemi nel caricamento del file. Possibile Lock su file.");
					}
					else
						System.out.println("Non ci sono reti nel Repository");
						noMsg = true;
					break;
				case 3:continua=false;break;
					
				default:System.out.println("Opzione non presente");break;
				
				// opzione nascosta, utile al debug
				case 4: 
					Parser myParser = new Parser();
					pn = myParser.parsePetriNet("test.xml");
			}
		}while(pn==null && continua);
		
		if(pn!=null){
			//pn.getInfo();
			sim.setNet(pn, id);
			if(!noMsg) System.out.println("Rete caricata correttamente.");
			if(this.graph!=null)
				this.graph.setVisible(false);
			this.graph = new GraphEditorTester(pn);
		}
	}
	
	
	
	/**
	 * Salva la rete sul server in un oggetto serializzato
	 */
	private void saveNet(){
		boolean overWrite=false;
		boolean error = false;
		try{
			if(pn==null){
				System.out.println("\nATTENZIONE!!! Non è stata caricata alcuna rete di Petri.");
				return;
			}
			String name;
			
			int check = 0;
			do {
				name=Service.leggiStringa("Inserisci il nome della rete:" );
				check = disp.sendNet(pn,name ,overWrite);
				switch(check) {
				case -1:
					overWrite=Service.risposta("Il file esiste già!!! Si desidera sovrascriverlo?");
					if(!overWrite) continue; else check = disp.sendNet(pn,name ,overWrite);
					break;
				case -2: 
					System.out.println("\nIl server non è in grado di salvare la rete.");
					error=true;					
					break;
				case -3: 
					System.out.println("\nDevi inserire un nome di file valido.");
					break;					
				case -4:
					System.out.println("\nFile soggetto a Lock, impossibile utilizzare il nome scelto.");
					break;
				}
				
			} while(check!=0 && !error);
			
			if(!error)System.out.println("La marcatura è stata salvata correttamente.");
		}
		catch(Exception e){
			System.out.println("Problemi nel salvataggio della rete saveNet");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gestisce la modifica della priorità delle transizioni
	 */
	private void changeTransitionPriority(){
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
			sim.setNet(pn,id);
		} catch (RemoteException e) {
			System.out.println("Problemi con il server");
		}
	}

	/**
	 * Gestisce l'interazione utile all'avanzamento dello stato della rete
	 * @return true se � possibile proseguire la simulazione
	 * @return false se non � possibile proseguire la simulazione (DeadLock o errori)
	 */
	private boolean oneStepBeyond(){
		try{
			Vector<Transition> trans = sim.getSelectableTransition(id);
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
			
			if(sim.nextStep(transitionId[transitionChoice],id)) {
				pn = sim.getNet(id);
				this.graph.redraw(pn);
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
		
		//pn.getInfo();
		return true;
	}
	
	/**
	 * Gestisce la simulazione
	 */
	private void manageSimulation(){
		try{
			if(sim.getNet(id)==null){
				System.out.println("\nATTENZIONE!!! Non è stata caricata alcuna rete di Petri.");
				return;
			}
		}
		catch(RemoteException e){
			System.out.println("Problemi di connessione");
			e.printStackTrace();
		}
		
		System.out.println("\n\n\t\tINIZIO SIMULAZIONE");
		//this.graph = new GraphEditorTester(pn);
        
		boolean esci=false;
		
		
		//SFRUTTA LA VALUTAZIONE IN CORTOCIRCUITO
		while(!esci && oneStepBeyond())
			esci = !Service.risposta("\nContinuare la simulazione ");


		System.out.println("Fine della simulazione");
	}
	
	/**
	 * Interrompe la simulazione e interrompe la comunicazione con il server
	 * @throws RemoteException 
	 */
	private void stopClient() throws RemoteException{
		boolean exit=true;
		exit=Service.risposta("Sei sicuro di voler uscire? ");
		if(!exit)return;
		
		sim.resetSimulator(id);	
		sim.deleteClient(id);
		if(graph!=null)
			this.graph.setVisible(false);
		System.out.println("Termine del programma.");
	}
	
	/**
	 * main
	 * @param args
	 */
	
	public static void main(String[] args) {
		
		
		System.setSecurityManager(new RMISecurityManager());
		
		try {
            TetClient tc = new TetClient();

            tc.mainMenu = new Menu("Menu Principale",MAIN_MENU_OPTION);
            do{
            	switch(tc.mainMenu.scelta()){
            		case 1:tc.loadNet();break;
            		case 2:tc.manageSimulation();break;
	            	case 3:tc.saveNet();break;
	            	case 4:tc.changeTransitionPriority();break;
	            	case 5:tc.stopClient();
	            		//continua=false;break;
	            		System.exit(0);break;
	            	default:break;
            	}
            }while(true);

        } catch (Exception e) {
            // Something wrong here
            e.printStackTrace();
        }
		
	}
	
	
}
