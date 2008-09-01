package tetClient;

import javax.swing.JFrame;

import tetPns.PetriNet;


public class GraphEditorTester extends JFrame {
	NetGraph graphEditor;
	PetriNet pnz;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public GraphEditorTester(PetriNet pn) {
		this.pnz = pn;
		setTitle("T&TPnS Sim is fun... no, really!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.graphEditor = new NetGraph(900, 500, pnz);
		getContentPane().add(graphEditor);
		pack();
		setVisible(true);
	}
	
	public void redraw() {
		//System.out.println("redrawing");
		getContentPane().remove(this.graphEditor);
		this.graphEditor = new NetGraph(500, 500, pnz);
		getContentPane().add(graphEditor);
		setVisible(false);
		this.repaint();
		setVisible(true);
	}
	
	public static void main(String args[]) {
		//new GraphEditorTester();
	}
}