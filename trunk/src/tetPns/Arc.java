package tetPns;

/**
 * Arcs descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Arc {
	private Element sourceElement;
	private Element targetElement;
	String arcId;
	
	public	Arc () {
		this.sourceElement = null;
		this.targetElement = null;
		this.arcId = null;
	}
	
	public void setSourceElement(Element start) {
		this.sourceElement = start;
	}
	
	public void setTargetElem(Element end) {
		this.targetElement = end;
	}	
	
	public void setId(String id) {
		this.arcId = id;
	}
}
