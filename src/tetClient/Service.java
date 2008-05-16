package tetClient;

/*
Questa classe contiene vari metodi static di utilita' generale
*/
import java.io.*;
import java.util.*;
import java.text.*;

public class Service
{
	private final static String ERRORE_TASTIERA = "Sono stati riscontrati gravi problemi con la tastiera.\n";
	private final static String EXIT_MSG = "Il programma verrà terminato!!!\n";
	private final static String STRINGA_VUOTA="Non è ammesso inserire stringhe vuote!!!";
	private final static String RIPETI="Ripetere l'inserimento";
	private final static String ERRORE_NUMERO = "La stringa inserita non e' nel formato numerico richiesto";
	private final static String MESS_LIMITI = "Attenzione i valori ammessi sono nell'intervallo: ";
	
	//private final static String ACAPO = "\n";
	private final static String SPAZIO=" ";
	private final static String SINO="[S/N]";
	private final static String SI="s";
	//private final static String NO="n";

	private static BufferedReader input = new BufferedReader (new InputStreamReader (System.in));
	
	private static Random rand = new Random();

	public static String leggiStringa(String msg)
	{
		System.out.print(msg);
		String letta=null;
		
		try
		{
			do
			{
				letta=input.readLine();
			}while(stringaVuota(letta));
		}
		
		catch (IOException e)
		{
			System.out.println(ERRORE_TASTIERA);
			System.out.println(EXIT_MSG);
			System.exit(1);
		}
		
		return letta.toLowerCase();
	}
	
	public static String leggiStringaAncheVuota(String msg)
	{
		System.out.print(msg);
		String letta=null;
		
		try
		{
				letta=input.readLine();
		}
		
		catch (IOException e)
		{
			System.out.println(ERRORE_TASTIERA);
			System.out.println(EXIT_MSG);
			System.exit(1);
		}
		
		return letta.toLowerCase();
	}
	
	public static boolean stringaVuota(String s)
	{
		if(s.length()==0)
		{
			System.out.println(STRINGA_VUOTA);
			System.out.println(RIPETI);
			return true;
		}
		return false;
	}
	
	public static Vector<String> spezzaStringa(String a)
	{
		Vector<String> t=new Vector<String>();
		int indexSpazio=0;
		boolean fine=false;
		
		for(int i=0;!fine;i=(indexSpazio+1))
		{
			try
			{
				indexSpazio=a.indexOf(SPAZIO,i);
				t.add(a.substring(i,indexSpazio));
			}
			catch(StringIndexOutOfBoundsException e)
			{
				t.add(a.substring(i,a.length()));
				fine=true;
			}
		}
		return t;
	}
	
	public static boolean risposta(String domanda)
	{
		String risp=leggiStringa(domanda+SINO);
		if(risp.equalsIgnoreCase(SI))
			return true;
		return false;
	}
	
	public static double leggiDouble (String messaggio, double min, double max)
	 {
		 double scelto = 0;
		 boolean neiLimiti = false;
		 do
			{
			 scelto = leggiDouble(messaggio+min+"-"+max);
			 if ( (scelto >= min) && (scelto <= max) )
				neiLimiti = true;
			 else
				System.out.println (MESS_LIMITI + min +"-" + max);
				
			} while (!neiLimiti);
			
		 return scelto;
	 }

	
	public static double leggiDouble (String messaggio)
	 {
		 boolean corretto = false;
		 double risultato = 0;
		 do
			{
			 	try
			 	{
			 		risultato = Double.parseDouble( leggiStringa(messaggio) );
			 		corretto = true;
			 	}
			 
			 	catch (NumberFormatException e)
			 	{
			 		System.out.println(ERRORE_NUMERO);
			 		System.out.println(RIPETI);
			 	}
			}while (!corretto);
			
		 return risultato;
	 }
	
	public static int leggiInt (String messaggio)
	 {
		 boolean corretto = false;
		 int risultato = 0;
		 do
			{
			 try
				{
				 risultato = Integer.parseInt( leggiStringa(messaggio) );
				 corretto = true;
				}
			 catch (NumberFormatException e)
				{
				 System.out.println(ERRORE_NUMERO);
				 System.out.println(RIPETI);
				}
				
			} while (!corretto);
			
		 return risultato;
	 }

  public static int leggiInt (String messaggio, int min, int max)
	 {
		 int scelto = 0;
		 boolean neiLimiti = false;
		 do
			{
		   scelto = leggiInt(messaggio+"("+min+"-"+max+"):");
			 if ( (scelto >= min) && (scelto <= max) )
				neiLimiti = true;
			 else
				System.out.println (MESS_LIMITI + min +"-" + max);
				
			} while (!neiLimiti);
			
		 return scelto;
	 }

  	public static int estraiIntero(int min, int max)
	{
  		int range = max + 1 - min;
  		int casual = rand.nextInt(range);
  		
  		return casual + min;
	}
	
	public static double estraiDouble(double min, double max)
	{
		double range = max - min;
		double casual = rand.nextDouble();
		double posEstratto = range*casual;
	 
		return posEstratto + min;
	}
 
	static String formatta (double d, int numDecimali, boolean conSegno )
	{
		StringBuffer buf = new StringBuffer("0");
		if (numDecimali > 0)
			buf.append(".");
		for (int i=1; i<= numDecimali; i++)
			buf.append("0");
		
		String formato = buf.toString();
		
		//creo un formattatore che stampa solo due decimali
		DecimalFormat formattatore = new DecimalFormat(formato); 
		
		// Il formattatore usera' come separatore il punto o la virgola
		// a seconda delle impostazioni locali del computer utilizzato
	 
		String daScrivere = formattatore.format(d);
	 
		if (conSegno)
			if (d >= 0)
				daScrivere = "+" + daScrivere;
			
		return daScrivere;	
	
	}
	
	public static void salvaEdEsci(File f,Object daSalvare)
	{
		salvaSingoloOggetto(f,daSalvare);
		System.out.print("\nGRAZIE PER AVER USATO QUESTO PROGRAMMA!!\n");
		System.exit(1);
	}
	
	public static Object caricaSingoloOggetto (File f)
	{
		 Object letto = null;
		 ObjectInputStream ingresso = null;
			
		 try
		 {
			 ingresso = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			 letto = ingresso.readObject();		
		 }
		 
		 catch (FileNotFoundException excNotFound)
		 {
			 System.out.println("ATTENZIONE: NON TROVO IL FILE " + f.getName() );
		 }
		 
		 catch (IOException excLettura)
		 {
			 System.out.println("ATTENZIONE: PROBLEMI CON LA LETTURA DEL FILE " + f.getName() );
		 }
		 
		 catch (ClassNotFoundException excLettura)
		 {
			 System.out.println("ATTENZIONE: PROBLEMI CON LA LETTURA DEL FILE " + f.getName() );
		 }
		 
		 finally
			{
			 if (ingresso != null)
				{
				 try 
					{
					 	ingresso.close();
					}
				 catch (IOException excChiusura)
					{
			 			System.out.println("ATTENZIONE: PROBLEMI CON LA CHIUSURA DEL FILE " + f.getName() );
					}
				}//if
			} // finally

		 return letto;
		  
	 } // metodo caricaSingoloOggetto
	
	
	public static void salvaSingoloOggetto (File f, Object daSalvare)
	 {
		 ObjectOutputStream uscita = null;
			
		 try
			{
			 uscita = new ObjectOutputStream(new BufferedOutputStream (new FileOutputStream(f)));
			 uscita.writeObject(daSalvare);
				
			}
		 catch (IOException excScrittura)
			{
			 System.out.println("ATTENZIONE: PROBLEMI CON LA SCRITTURA DEL FILE " + f.getName() );
			}
		 
  	 finally
			{
			 if (uscita != null)
				{
				 try 
					{
				   uscita.close();
					}
				 catch (IOException excChiusura)
					{
			 			System.out.println("ATTENZIONE: PROBLEMI CON LA CHIUSURA DEL FILE " + f.getName() );
					}
				}//if
			} // finally

	} // metodo salvaSingoloOggetto
	
	
}
