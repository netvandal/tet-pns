package tetPns;

import java.io.Serializable;

/**
 * @author Michele Tameni, Alessio Troiano
 *
 */
public abstract class Element implements Serializable{
	public Element(){
	}
	
	public abstract void setId(String value);
	public abstract String getId();
	public abstract void getInfo();
}
