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
	ParserMainHandler pmh;
		
	public Parser() {
		
	    try {
	    	parser = XMLReaderFactory.createXMLReader();
	    }
	    catch (SAXException e) {
	    	System.err.println("createXMLReader failed.");
	    	return;
	    }
	    pmh = new ParserMainHandler();
	    parser.setContentHandler(pmh);
	}
	
	public PetriNet parsePetriNet(String toParse){
		try{
			parser.parse(toParse);
		}
		catch(Exception e){
			System.err.println(e.toString());
		}
		
		return pmh.getPetriNet();
	}
	
}


