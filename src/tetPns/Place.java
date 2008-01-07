package tetPns;

/**
 * Place descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */

public class Place extends Element {
	
	private int token = 0;
	private String placeId;
	
	public Place() {
		
	}

	public void addToken(int n) {
		this.token +=n;
	}
	
	public void subToken(int n){
		this.token -=n;
	}
	
	public int getToken(){
		return this.token;
	}

	public void setId(String value) {
		this.placeId = value;
	}
	
	public String getId() {
		return this.placeId;
	}
	
	public void getInfo() {
		System.out.println("Place " + this.placeId + " con " + this.token + " Token\n");		
	}
	
	
}
