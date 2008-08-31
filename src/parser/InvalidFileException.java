package parser;

import org.xml.sax.SAXException;

/**
 * Questa classe rappresenta l'eccezione che viene scatenata nel caso in cui il file PNML caricato
 * risulti invalido o mal formato.
 * 
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */

public class InvalidFileException extends SAXException{

	private static final long serialVersionUID = -7595703333463328029L;

	public InvalidFileException(){
		super("Problemi con il parsing del file");
	}
	
	public String toString(){
		return getMessage() + ": FILE NON VALIDO";
	}
}
