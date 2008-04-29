package tetClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Vector;

import parser.Parser;

import tetPns.PetriNet;
import tetPns.Transition;
import tetServer.IDispenser;
import tetServer.ISimulator;


public class TetClient {
	public static void main(String[] args) {
		
		System.setSecurityManager(new RMISecurityManager());
		
		try {
            Registry reg = LocateRegistry.getRegistry();
            //Thread.sleep(10000);
            ISimulator s = (ISimulator) reg.lookup("SIMULATOR");
            //Thread.sleep(7000);

            //testing 
    		Parser myParser = new Parser();
    		PetriNet pn = myParser.parsePetriNet("test.xml");
            pn.getInfo();
    		
    		
            s.setNet(pn);
            Vector<Transition> q = s.getSelectableTransition();
            for(Transition t : q)
				t.getInfo();
            
            System.out.println("\n\nCosa vuoi far scattare?");
    		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

			String id = null;
			
			System.out.println("\t\tSCATTA " + (id = input.readLine()));
            if(s.nextStep(id))
             pn = s.getNet();
            else  System.out.println("\n\nErrore nel far scattare la transizione");
            pn.getInfo();
            
        } catch (Exception e) {
            // Something wrong here
            e.printStackTrace();
        }
		
	}
	
}
