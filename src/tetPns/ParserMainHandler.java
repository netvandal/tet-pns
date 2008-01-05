package tetPns;

import org.xml.sax.*;

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
	
	  public void startDocument() throws SAXException{
	    this.placeCount = 0;
	    this.transitionCount = 0;
	    this.arcCount = 0;
	  }	
	
	public void startElement (String uri, String name,
			String qName, Attributes atts) {
		
		
		
		if (qName.equals("place")) this.placeCount++;
		else if(qName.equals("transition")) this.transitionCount++;
		else if(qName.equals("arc")) this.arcCount++;

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

	public void endElement (String uri, String name, String qName)
	{
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		
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
