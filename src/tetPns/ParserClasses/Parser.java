package tetPns.ParserClasses;

import java.util.Vector;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import tetPns.Arc;
import tetPns.Place;
import tetPns.Transition;


/**
 * This is the main class that parse a pnml for building a Petri Net Model
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Parser {
		
	public Parser() {
		XMLReader parser;
	    try {
	    	parser = XMLReaderFactory.createXMLReader();
	    }
	    catch (SAXException e) {
	    	System.err.println("createXMLReader failed.");
	    	return;
	    }
	    parser.setContentHandler(new ParserMainHandler());
	    try {
	    	parser.parse("test.xml");
	    }
	    catch (Exception e) {
	    	System.err.println(e.toString());
	    }
	}
	
}
