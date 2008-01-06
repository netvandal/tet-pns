package tetPns;

/**
 * Arcs descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */
public class Arc {
	private Object sourceElement;
	private Object targetElement;
	String arcId;
	
	public	Arc () {
		this.sourceElement = null;
		this.targetElement = null;
		this.arcId = null;
	}
	
	public void setSourceElement(Object start) {
		this.sourceElement = start;
	}
	
	public void setTargetElement(Object end) {
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
		sourceId = targetId = null;
		
		// FIXME da rivedere se c'è un metodo + fico
		Class c = this.sourceElement.getClass();
		String cName = c.getName();
		if(cName.equals("tetPns.Place")) sourceId = ((Place)sourceElement).getId();
		if(cName.equals("tetPns.Transition")) sourceId = ((Transition)sourceElement).getId();
		
		c = this.targetElement.getClass();
		cName = c.getName();
		if(cName.equals("tetPns.Place")) targetId = ((Place)targetElement).getId();
		if(cName.equals("tetPns.Transition")) targetId = ((Transition)targetElement).getId();
		System.out.println("Arco " + this.arcId + " Source " + sourceId + " Target " + targetId);
	}
}
