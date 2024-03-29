package tetClient;

import javax.swing.JFrame;

import tetPns.PetriNet;

/**
 * Classe utile alla gestione del grafico della rete di petri. 
 * Si preoccupa di creare la finestra, impostarne le dimensione e renderla visibile
 * Inoltre gestisce anche l'aggiornamento del grafico ad ogni passo della simulazione
 * @author michele
 *
 */

public class GraphEditorTester extends JFrame {
	NetGraph graphEditor;
	PetriNet pnz;

	private static final long serialVersionUID = 1L;
	
	private static final int  altezzaFinestra = 500;
	private static final int largFinestra = 900;

	public GraphEditorTester(PetriNet pn) {

		
		this.pnz = pn;
		setTitle("T&TPnS Sim is fun... no, really!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.graphEditor = new NetGraph(largFinestra, altezzaFinestra, pnz);
		getContentPane().add(graphEditor);
		pack();
		setVisible(true);
	}
	
	public void redraw(PetriNet pn) {
		//System.out.println("redrawing");
		getContentPane().remove(this.graphEditor);
		this.graphEditor = new NetGraph(largFinestra, altezzaFinestra, pn);
		getContentPane().add(graphEditor);
		setVisible(false);
		this.repaint();
		setVisible(true);
	}
	
	public static void main(String args[]) {
		//new GraphEditorTester();
	}
}