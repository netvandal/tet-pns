package tetClient;

import javax.swing.JFrame;


public class GraphEditorTester extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public GraphEditorTester() {
		setTitle("Piccolo Graph Editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		NetGraph graphEditor = new NetGraph(500, 500);
		getContentPane().add(graphEditor);
		pack();
		setVisible(true);
	}
	

	public static void main(String args[]) {
		new GraphEditorTester();
	}
}