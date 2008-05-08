package tetClient;

import java.io.*;

public class Menu implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private final static String SEPARATORE="-";
	private final static String SPAZIO=" ";
	private final static int offset=3;
	
	private String titolo;
	private String[] opzioni;
	private int larghezzaColonna;
	
	public Menu(String t,String[] op)//costruttore del menù
	{
		titolo=t;
		opzioni=op;
		larghezzaColonna=setColonna();
	}
	
	//viene settata la larghezza della colonna come la lunghezza dell'opzione più lunga
	public int setColonna()
	{
		int max=titolo.length();
		for(int i=0;i<opzioni.length;i++)
		{
			if(opzioni[i].length()>max)
				max=opzioni[i].length();
		}
		return (max+offset);
	}
	
	public int getOpzioniSize()
	{
		return opzioni.length;
	}
	
	//stampa il menù
	public void stampa()
	{
		stampaIntestazione();//stampa l'intestazione del menù
		
		for(int i=0;i<opzioni.length;i++)//stampa titte le opzioni
			System.out.println((i+1)+SEPARATORE+opzioni[i]);
		
		//stampa un separatore
		System.out.println(aggiungiSimbolo(SEPARATORE,larghezzaColonna));
	}
	
	//stampa l'intestazione del menù
	public void stampaIntestazione()
	{
		//viene stampato un separatore in base alla largheza della colonna
		System.out.println(aggiungiSimbolo(SEPARATORE,larghezzaColonna));
		//viene stampato il titolo del menù centrato nella colonna
		System.out.println(formattaStringhe(titolo,larghezzaColonna,true));
		//viene stampato un altro separatore
		System.out.println(aggiungiSimbolo(SEPARATORE,larghezzaColonna));
	}
	
	//stampa il menù permette di effettuare una scelta fra le ozioni
	public int scelta()
	{
		stampa();
		return Servizi.leggiInt("Scelta",1,opzioni.length);
	}
	
	//Aggiunge n volte un simbolo ad una stringa
	public static String aggiungiSimbolo(String simbolo,int n)
	{
		StringBuffer simboli = new StringBuffer();
		for(int i=0;i<n;i++)
			simboli.append(simbolo);
		return simboli.toString();
	}
	
	//formatta l'elemento di una colonna al centro(centrato=true)
	//o sulla sx (centrato=false)
	public String formattaStringhe(String a,int larg,boolean centrato)
	{
		StringBuffer daFormattare= new StringBuffer();
		
		if(a.length()<larg)
		{
			if(centrato)
			{
				int spaziAggiunti=(int) (larg-a.length())/2;
				daFormattare.append(aggiungiSimbolo(SPAZIO,spaziAggiunti));
				daFormattare.append(a);
				daFormattare.append(aggiungiSimbolo(SPAZIO,larg-a.length()-spaziAggiunti));
			}
			else
			{
				daFormattare.append(a);
				daFormattare.append(aggiungiSimbolo(SPAZIO,larg-a.length()));
			}
			
			return daFormattare.toString();
		}
		else
		{
			return a.substring(0,larg);
		}
	}//formattaStringhe

}
