package parser;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import tetPns.PetriNet;


/**
 * Classe principale che si occupa di effettuare il parsing dei file PNML
 * richiamando ParserMainHandler e il Validator.
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 *
 */
public class Parser {
	
	XMLReader parser;
	Validator validator;
	
	/**
	 * Costruttore della classe. 
	 * Vengono creati e inizializzati tutti gli oggetti necessari al parsing.
	 */
	
	public Parser() {
		
	    try {
	    	parser = XMLReaderFactory.createXMLReader();
	    }
	    catch (SAXException e) {
	    	System.err.println("createXMLReader failed.");
	    	return;
	    }
	    validator = new Validator();
	    parser.setContentHandler(new ParserMainHandler(validator));
	}
	
	/**
	 * 
	 * Viene effettuato il parsing, e in caso di file valido, viene ritornata un oggetto PetriNet
	 * 
	 * @param toParse nome del file di cui effettuare il parsng
	 * @return PetriNet ritorna l'oggetto della rete di petri descritta nel file
	 * @return null in caso di eccezioni o invaliditˆ del file
	 */
	public PetriNet parsePetriNet(String toParse){
		try{
			parser.parse(toParse);
			validator.validate();
			return validator.getPetriNet();
		}
		catch(SAXException e){
			return null;
		}
		catch(Exception e){
			System.out.println("Problemi di I/O nel parser.");
			return null;
		}
	}
	
}


