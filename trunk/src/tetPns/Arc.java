package tetPns;

/**
 * Arcs descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */


public class Arc extends Element{

	final static int WEIGHT =1;
	
	private Element sourceElement;
	private Element targetElement;
	private int weight = WEIGHT;
	String arcId;
	
	public	Arc (String id, Element s, Element t) {
		this.sourceElement = s;
		this.targetElement = t;
		this.arcId = id;
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
	
	public Element getSourceElement(){
		return this.sourceElement;
	}
	
	public Element getTargetElement(){
		return this.targetElement;
	}
	
	public String getId() {
		return this.arcId;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public void getInfo() {
		String sourceId, targetId;
	
		sourceId = sourceElement.getId();
		targetId = targetElement.getId();
		System.out.println("Arco " + arcId + "\tSource:" + sourceId + "\tTarget:" + targetId);
	}
}
