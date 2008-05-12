package parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;


public class ParserMainHandler implements ContentHandler{
	
	private Validator validator;
	
	private String elementId;
	private String posX, posY, labelX, labelY;
	private String temp1,temp2;
	
	private boolean inPlace, inTrans, inArc,inToken, inInitialMarking, inName;

	public ParserMainHandler(Validator v){
		validator=v;
	}
	
	public void startDocument() throws SAXException {
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
		else if(name.equals("text") && inPlace && inInitialMarking) inToken=true;
		
	}
	
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		String content = new String(ch,start,length);
		if(inPlace && inInitialMarking && inToken) temp1=content; //token del place	
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
		else if(name.equals("InitialMarking"))inInitialMarking=false;
		else if(name.equals("text") && inToken) inToken=false;
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
