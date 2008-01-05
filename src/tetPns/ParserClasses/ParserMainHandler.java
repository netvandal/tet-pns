package tetPns.ParserClasses;

import org.xml.sax.*;

import tetPns.Arc;
import tetPns.Element;
import tetPns.Place;
import tetPns.Transition;

/**
 * The main handler for the pnml parser
 * @author michele
 *
 */

public class ParserMainHandler implements ContentHandler {
	public ParserMainHandler() {
	super();	
	}
	
	int placeCount, transitionCount, arcCount; 
	Place lastPlace;
	Transition lastTrans;
	Arc lastArc;
	boolean inPlace, inToken, inArc, inTransition, inInitialMarking;
	
	
	  public void startDocument() throws SAXException{
	    this.placeCount = 0;
	    this.transitionCount = 0;
	    this.arcCount = 0;
	    this.lastPlace = null;
	    this.lastArc = null;
	    this.lastTrans = null;
	    this.inPlace = this.inToken = this.inArc = this.inTransition = this.inInitialMarking = false;
	  }	
	
	public void startElement (String uri, String name,
		String qName, Attributes atts) {
			
		if (qName.equals("place")) {
			
			this.inPlace = true;
			this.placeCount++;
			this.lastPlace = new Place();
			int length = atts.getLength();
			for (int i=0; i<length; i++) {
				String nameAtt = atts.getQName(i);
				if(nameAtt.equals("id")) this.lastPlace.setId(atts.getValue(i));
			}
			
			
		}
		else if(qName.equals("transition")) {
			this.transitionCount++;
			this.inTransition = true;
			this.lastTrans = new Transition(0);
			int length = atts.getLength();
			for (int i=0; i<length; i++) {
				String nameAtt = atts.getQName(i);
				if(nameAtt.equals("id")) this.lastTrans.setId(atts.getValue(i));
			}			

		}
		else if(qName.equals("arc")) {
			this.arcCount++;
			this.lastArc = new Arc();
			int length = atts.getLength();
			for (int i=0; i<length; i++) {
				String nameAtt = atts.getQName(i);
				if(nameAtt.equals("id")) this.lastArc.setId(atts.getValue(i));
				else if(nameAtt.equals("source")); //devo settare elemento di inizio
				else if(nameAtt.equals("target")); //devo settare elemento fine
			}					
		}
		else if(qName.equals("initialMarking") && this.inPlace) this.inInitialMarking = true;
		else if(qName.equals("text") && this.inPlace && this.inInitialMarking) this.inToken=true;

//		
//		int length = atts.getLength();
//		for (int i=0; i<length; i++) {
//			String nameAtt = atts.getQName(i);
//
//			String value = atts.getValue(i);
//			//System.out.println("Attributes: " + nameAtt + " value : " + value);
//			
//		}
		

	}

	public void endElement (String uri, String name, String qName) {
		if(qName.equals("place") && this.inPlace) {
			this.lastPlace.getInfo();
			this.lastPlace = null;
			this.inPlace = false;
		}
		else if(qName.equals("transition")) {
			this.lastTrans.getInfo();
			this.lastTrans = null;
			this.inTransition = false;
			
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
		System.out.println("Places " + this.placeCount);
		System.out.println("Transitions " +this.transitionCount);
		System.out.println("Arcs " + this.arcCount);
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
