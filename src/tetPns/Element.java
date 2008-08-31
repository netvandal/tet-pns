package tetPns;

import java.io.Serializable;

/**
 * Classe generica degli elementi della rete
 * @author Michele Tameni, Alessio Troiano
 * @version 1.0
 */
public abstract class Element implements Serializable{
	public Element(){
	}
	
	public abstract void setId(String value);
	public abstract String getId();
	public abstract void getInfo();
}
