package parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import tetPns.Arc;
import tetPns.PetriNet;
import tetPns.Place;
import tetPns.Transition;


/**
 * The main handler for the pnml parser
 * @author michele
 */

public class ParserMainHandler implements ContentHandler {
	
	
	int placeCount, transitionCount, arcCount; 
	boolean inPlace, inToken, inInitialMarking;
	private Place lastPlace;
	private Transition lastTrans;
	private Arc lastArc;
	
	private PetriNet pNet;
	
	
	public ParserMainHandler() {
		
	}
	
	public void startDocument() throws SAXException{
		this.placeCount = 0;
		this.transitionCount = 0;
		this.arcCount = 0;
		this.lastPlace = null;
		this.lastArc = null;
		this.lastTrans = null;
		this.inPlace = this.inToken = this.inInitialMarking = false;
		pNet = new PetriNet();
	}	
	
	public void startElement (String uri, String name,
		String qName, Attributes atts) {
			
		if (qName.equals("place")) {
			this.inPlace = true;
			this.placeCount++;
			this.lastPlace = new Place();
			this.lastPlace.setId(atts.getValue("id"));
			
		}
		else if(qName.equals("transition")) {
			this.transitionCount++;
			this.lastTrans = new Transition(Integer.parseInt(atts.getValue("priority")));
			this.lastTrans.setId(atts.getValue("id"));
			
		}
		else if(qName.equals("arc")) {
			this.arcCount++;
			this.lastArc = new Arc();
			this.lastArc.setId(atts.getValue("id"));
			this.lastArc.setSourceElement(pNet.getElementById(atts.getValue("source")));
			this.lastArc.setTargetElement(pNet.getElementById(atts.getValue("target")));
		}
		else if(qName.equals("initialMarking") && this.inPlace) this.inInitialMarking = true;
		else if(qName.equals("text") && this.inPlace && this.inInitialMarking) this.inToken=true;
	

	}

	public void endElement (String uri, String name, String qName) {
		if(qName.equals("place") && this.inPlace) {
			//this.lastPlace.getInfo();
			pNet.addPlace(this.lastPlace);
			this.lastPlace = null;
			this.inPlace = false;
		}
		else if(qName.equals("transition")) {
			//this.lastTrans.getInfo();
			pNet.addTransition(this.lastTrans);
			this.lastTrans = null;			
		}
		else if(qName.equals("arc")) {
			pNet.addArc(this.lastArc);
			this.lastArc = null;
			//this.lastArc.getInfo();
		}
		
		else if(qName.equals("text") && this.inPlace && this.inToken) {
			this.inToken = false;
		}
		else if(qName.equals("initialMarking")) {
			this.inInitialMarking = false;
		}
		else if(qName.equals("text") && this.inToken) this.inToken = false;		
	}

	public void characters(char[] ch, int start, int len) {
		String content = new String(ch,start,len);
		if(this.lastPlace != null && this.inPlace && this.inInitialMarking && this.inToken)  {
			this.lastPlace.addToken(Integer.parseInt(content));
		}
	}

	public void endDocument() throws SAXException {
		//pNet.getInfo();
	}
	
	public PetriNet getPetriNet(){
		return this.pNet;
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub
	}
	
}

