package parser;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import tetPns.PetriNet;


/**
 * This is the main class that parse a pnml for building a Petri Net Model
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Parser {
	
	XMLReader parser;
	Validator validator;
		
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


