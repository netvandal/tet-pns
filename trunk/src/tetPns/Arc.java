package tetPns;

/**
 * Arcs descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Arc {
	private Element startElement;
	private Element endElement;
	String arcId;
	
	public	Arc () {
		this.startElement = null;
		this.endElement = null;
		this.arcId = null;
	}
	
	public void setStartElem(Element start) {
		this.startElement = start;
	}
	
	public void setEndElem(Element end) {
		this.endElement = end;
	}	
	
	public void setId(String id) {
		this.arcId = id;
	}
}
