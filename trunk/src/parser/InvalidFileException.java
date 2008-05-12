package parser;

import org.xml.sax.SAXException;

public class InvalidFileException extends SAXException{

	private static final long serialVersionUID = -7595703333463328029L;

	public InvalidFileException(){
		super("Problemi con il parsing del file");
	}
	
	public String toString(){
		return getMessage() + ": FILE NON VALIDO";
	}
}
