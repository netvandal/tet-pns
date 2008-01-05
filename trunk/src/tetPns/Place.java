package tetPns;

/**
 * Place descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */

public class Place extends Element {
	private int tokenQuantity = 0;
	
	String placeId;
	
	public Place() {
		
	}

	public void addToken(int n) {
		this.tokenQuantity +=n;
	}

	public void setId(String value) {
		// TODO Auto-generated method stub
		this.placeId = value;
	}
	
	public String getId() {
		return this.placeId;
	}
	
	public void placeInfo() {
		System.out.println("Creato place " + this.placeId + " con " + this.tokenQuantity + " Token");		
	}
	
	
}
