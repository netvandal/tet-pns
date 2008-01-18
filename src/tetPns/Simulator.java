package tetPns;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import parser.Parser;

/**
 * Simulator is the main class that manage all the simulation
 * of a Petri Net
 * @author Michele Tameni, Alessio Troiano
 */


public class Simulator {

	PetriNet pn;
	Parser myParser;
	
	public static void main(String[] args) throws IOException {
		Simulator sim =new Simulator();
		sim.initSimulation();
		sim.manageSimulation();
	}

	/**
	 * Initialize the simulator, parsing the XML file and
	 * creating the Petri Net.
	 */
	private void initSimulation() {
		myParser = new Parser();
		pn = myParser.parsePetriNet("test.xml");
		pn.createLinks();
		pn.setPriorities(); //Metodo solo per debug. Da eliminare.
		pn.orderTransitionsByPriority();	
		pn.getInfo();
	}
	
	private void manageSimulation() throws IOException{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("\n\n\nDOPO RIORDINO");
		pn.getInfo();
		Vector<Transition> q;
		boolean exit = false;
		int iter =1;
		
		System.out.println("\n\nOCCHIO!!! COMINCIA IL CICLO \n" +
				"(Attento ai blocchi della moooorteeeee)");
		while((q = pn.getEnabledTransitionsWithHighestPriority())!= null && !exit){
			System.out.println("ITERAZIONE NUMERO " + iter);
			System.out.println("ITERAZIONI ABILITATE");
			for(Transition t : q)
				t.getInfo();
			if(q.size() == 1){
				System.out.println("\t\tSCATTA " + q.firstElement().getId());
				pn.fireTransition(q.firstElement().getId());
			}
			else{
				String s;
				System.out.println("\n\nCosa vuoi far scattare?");
				System.out.println("\t\tSCATTA " + (s = input.readLine()));
				pn.fireTransition(s);
			}
			System.out.println("Info sulla rete:");
			pn.getInfo();
			System.out.println("\n\nVuoi Continuare? (y/n) : ");
			if(input.readLine().equalsIgnoreCase("n"))exit = true;
			iter++;
		}
		if(q == null)System.out.println("\n\n\nDEEEEEEEEEEEEAAAAAAAAAADLOOOOOOOOCK");
	}
}
