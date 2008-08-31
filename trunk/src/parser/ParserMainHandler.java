package parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * 
 * Classe necessaria alla lettura del file XML
 * Vengo descritti i tag da prendere in considerazione, i valori da salvare, e passa tutto al validator per il controllo 
 * di validitˆ.
 * 
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 *
 */

public class ParserMainHandler implements ContentHandler{
	
	private Validator validator;
	
	private String elementId;
	private String posX, posY, labelX, labelY;
	private String temp1,temp2;
	
	private boolean inPlace, inTrans, inArc,inToken, inInitialMarking, inName, inPriority, inPriorityValue;

	public ParserMainHandler(Validator v){
		validator=v;
	}
	
	// Inizializzazione delle variabili
	public void startDocument() throws SAXException {
		elementId=posX=posY=labelX=labelY=temp1=temp2=null;
	}
	

	// Descriviamo i tag rilevanti ai fini della creazione della rete di Petri
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		
		if(name.equals("place") && atts.getLength()==1){
			inPlace=true;
			elementId=atts.getValue("id");
		}
		else if(name.equals("transition") && atts.getLength()==1){
			inTrans=true;
			elementId=atts.getValue("id");
			//if(atts.getLength()==2) temp1=atts.getValue("priority");
		}
		else if(name.equals("arc") && atts.getLength()==3){
			elementId=atts.getValue("id");
			temp1=atts.getValue("source");
			temp2=atts.getValue("target");
			inArc=true;
		}
		else if(name.equals("position") && atts.getLength()==2){
			posX=atts.getValue("x");
			posY=atts.getValue("y");
		}
		else if(name.equals("name")) inName=true;
		else if(name.equals("offset") && atts.getLength()==2 && inName){
			labelX=atts.getValue("x");
			labelY=atts.getValue("y");
		}
		else if(name.equals("initialMarking")) inInitialMarking=true;
		else if(name.equals("value") && inPlace && inInitialMarking) inToken=true;
		else if(name.equals("priority") && inTrans) inPriority=true;
		else if(name.equals("value") && inPriority) inPriorityValue=true;
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String content = new String(ch,start,length);
		if(inPlace && inInitialMarking && inToken) {
			temp1=content; //token del place	
			//System.out.println("\n\n\tDENTRO IL PARSERMAINHANDLER \t valore:" + temp1);
		}
		
		if(inTrans && inPriority && inPriorityValue) temp1=content; //prioritˆ della transizione
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException{

		if(name.equals("place") && inPlace){
			validator.placeController(elementId,temp1,posX,posY,labelX,labelY);
			inPlace=false;
			elementId=posX=posY=labelX=labelY=temp1=temp2=null;
		}
		else if(name.equals("transition") && inTrans){
			validator.transitionController(elementId,temp1,posX,posY,labelX,labelY);
			inTrans=false;
			elementId=posX=posY=labelX=labelY=temp1=temp2=null;
		}
		else if(name.equals("arc") && inArc){
			validator.arcController(elementId,temp1,temp2);
			inArc=false;
			elementId=posX=posY=labelX=labelY=temp1=temp2=null;
		}
		else if(name.equals("name"))inName=false;
		else if(name.equals("initialMarking"))inInitialMarking=false;
		else if(name.equals("value") && inToken) inToken=false;
		else if(name.equals("priority")) inPriority=false;
		else if(name.equals("value") && inPriorityValue) inPriorityValue=false;
	}
	
	public void endDocument() throws SAXException {
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
