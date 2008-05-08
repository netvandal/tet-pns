package tetPns;

/**
 * Place descriptor
 * @author Michele Tameni, Alessio Troiano
 *
 */

public class Place extends Element {
	
	private int token = 0;
	private String placeId;
	private int myX = 0;
	private int myY = 0;
	private int labelX=0;
	private int labelY=0;
	
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
		System.out.println("Place " + this.placeId + " con " + this.token + " Token ; x,y = " + this.myX + "," + myY + " labelcoords: "+ labelX +","+ labelY +"\n");		
	}
	
	public void setCoordX (int x) {
		myX = x;
	}
	
	public void setCoordY(int y) {
		myY = y;
	}
	
	public int getXCoord () {
		return myX;
	}
	
	public int getYCoord () {
		return myY;
	}
	
	public void setLabelX (int x) {
		labelX = x;
	}
	
	public void setLabelY(int y) {
		labelY = y;
	}
	
	public int getLabelX () {
		return labelX;
	}
	
	public int getLabelY () {
		return labelY;
	}
	
}
