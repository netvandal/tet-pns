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
	
	public void setTargetElement(Element end) {
		this.targetElement = end;
	}	
	
	public void setId(String id) {
		this.arcId = id;
	}
	
	public String getId() {
		return this.arcId;
	}
	
	public void getInfo() {
		String sourceId, targetId;
	
		sourceId = sourceElement.getId();
		targetId = targetElement.getId();
		System.out.println("Arco " + arcId + " Source " + sourceId + " Target " + targetId);
	}
}
