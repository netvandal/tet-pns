package parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import tetPns.Arc;
import tetPns.PetriNet;
import tetPns.Place;
import tetPns.Transition;

public class ParserMainHandler2 implements ContentHandler{
	
	private PetriNet pn;
	private Place lastPlace;
	private Transition lastTrans;
	private Arc lastArc;
	
	private String elementId;
	private String posX, posY, labelX, labelY;
	private String temp1,temp2;
	
	private boolean inPlace, inTrans, inToken, inInitialMarking, inPosPlace, inPosPlaceY, inPosPlaceX, inName;

	public void startDocument() throws SAXException {
		lastPlace=null;
		lastTrans=null;
		lastArc=null;
		elementId=posX=posY=labelX=labelY=temp1=temp2=null;
	}
	
	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		
		if(name.equals("place") && atts.getLength()==1){
			inPlace=true;
			elementId=atts.getValue("id");
		}
		else if(name.equals("transition") && (atts.getLength()==1 || atts.getLength()==2)){
			inTrans=true;
			elementId=atts.getValue("id");
			if(atts.getLength()==2) temp1=atts.getValue("priority");
		}
		else if(name.equals("arc") && atts.getLength()==3){
			elementId=atts.getValue("id");
			temp1=atts.getValue("source");
			temp2=atts.getValue("target");
		}
		else if(name.equals("position") && atts.getLength()==2){
			posX=atts.getValue("x");
			posY=atts.getValue("y");
		}
		else if(name.equals("name")) inName=true;
		else if(name.equals("offset") && atts.getLength()==2 && inName){
			labelX=atts.getValue("x");
			labelY=atts.getValue("x");
		}
		else if(name.equals("initialMarking")) inInitialMarking=inPlace;
		else if(name.equals("text") && inPlace && inInitialMarking) inToken=true;
		
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String content = new String(ch,start,length);
		if(inPlace && inInitialMarking && inToken) temp1=content;//token del place		
	}

	public void endDocument() throws SAXException {
		if(inPlace){
			lastPlace = Validator.placeController(elementId,temp1,posX,posY,labelX,labelY);
			inPlace=false;
		}
		
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		
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
