package tetClient;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import parser.Parser;
import tetPns.PetriNet;
import tetPns.Place;
import tetPns.Transition;
import tetPns.Arc;

import edu.umd.cs.piccolo.PCanvas;
import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PDragEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.event.PInputEventFilter;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolox.util.PNodeLocator;

public class NetGraph extends PCanvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NetGraph(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		
		/*
		 * Test, da eliminare
		 */
		
		
		Parser myParser = new Parser();
		PetriNet pn = myParser.parsePetriNet("test.xml");
		
        // Initialize, and create a layer for the edges
        // (always underneath the nodes)
		PLayer nodeLayer = getLayer();
		PLayer edgeLayer = new PLayer();
		getRoot().addChild(edgeLayer);
		getCamera().addLayer(0, edgeLayer);
		//Random random = new Random();

		PPath node = null;
		int totNode = 0;
          // Create some random nodes
		// Each node's attribute set has an ArrayList to store associated edges
		// FIXME fa casino se gli id di place/transizioni sono uguali!
		
		// disegno tutti i places
		for(Place place : pn.getPlaces()) {
			place.getInfo();
			node = PPath.createEllipse(place.getXCoord(), place.getYCoord(), 20, 20);
			node.addAttribute("edges", new ArrayList());
			node.addAttribute("id", place.getId());
			node.addAttribute("type", "place");
			PText text = new PText(Integer.toString(place.getToken()));
			node.addChild(text);
			
			PNodeLocator pbl = new PNodeLocator(node);
			 		text.setOffset(
			 			pbl.locateX() - (0.5 * text.getBounds().getWidth()),
			 			pbl.locateY() - (0.5 * text.getBounds().getHeight()));
			 		text.setPickable(false);
			
			
			nodeLayer.addChild(node);
			totNode++;
		}

		// disegno tutte le transizioni
		for(Transition transition : pn.getTransitions()) {
			transition.getInfo();
			node = PPath.createRectangle(transition.getXCoord(), transition.getYCoord(), 20, 20);

			node.addAttribute("edges", new ArrayList());
			node.addAttribute("id", transition.getId());
			node.addAttribute("type", "transition");
			nodeLayer.addChild(node);
			totNode++;
		}
		int i;
		PNode nodeTest = null;
		//disegno tutti gli archi:
		for(Arc arc : pn.getArcs()) {
			arc.getInfo();
			String in = arc.getSourceElement().getId();
			String out = arc.getTargetElement().getId();
			PNode node1 = null, node2 = null;
			for(i=0;i<totNode;i++) {
				nodeTest = nodeLayer.getChild(i);
				if(nodeTest.getAttribute("id")==in) {
					node1 = nodeLayer.getChild(i);
				}
				if(nodeTest.getAttribute("id")==out) {
					node2 = nodeLayer.getChild(i); 
				}
			}
			
			if(node1 != null && node2 != null && node1!=node2) {
				PPath edge = new PPath();
				((ArrayList)node1.getAttribute("edges")).add(edge);
				((ArrayList)node2.getAttribute("edges")).add(edge);
				edge.addAttribute("nodes", new ArrayList());
				((ArrayList)edge.getAttribute("nodes")).add(node1);
				((ArrayList)edge.getAttribute("nodes")).add(node2);
				edgeLayer.addChild(edge);
				updateEdge(edge);			
			}
		}
			
		
			/*
                // Create some random edges
		// Each edge's attribute set has an ArrayList to store associated nodes
		for (int i = 0; i < 4; i++) {
			int n1 = random.nextInt(numNodes);
			int n2 = random.nextInt(numNodes);
			while (n1 == n2) {
				n2 = random.nextInt(numNodes);  // Make sure we have two distinct nodes.
			}
			
			PNode node1 = nodeLayer.getChild(n1);
			PNode node2 = nodeLayer.getChild(n2);
			PPath edge = new PPath();
			((ArrayList)node1.getAttribute("edges")).add(edge);
			((ArrayList)node2.getAttribute("edges")).add(edge);
			edge.addAttribute("nodes", new ArrayList());
			((ArrayList)edge.getAttribute("nodes")).add(node1);
			((ArrayList)edge.getAttribute("nodes")).add(node2);
			edgeLayer.addChild(edge);
			updateEdge(edge);
		}*/
		
                // Create event handler to move nodes and update edges
		nodeLayer.addInputEventListener(new PDragEventHandler() {
			{
				PInputEventFilter filter = new PInputEventFilter();
				filter.setOrMask(InputEvent.BUTTON1_MASK | InputEvent.BUTTON3_MASK);
				setEventFilter(filter);
			}
			public void mouseEntered(PInputEvent e) {
				super.mouseEntered(e);
				if (e.getButton() == MouseEvent.NOBUTTON) {
					e.getPickedNode().setPaint(Color.RED);
				}
			}
			
			public void mouseExited(PInputEvent e) {
				super.mouseExited(e);
				if (e.getButton() == MouseEvent.NOBUTTON) {
					e.getPickedNode().setPaint(Color.WHITE);
				}
			}
			
			protected void startDrag(PInputEvent e) {
				super.startDrag(e);
				e.setHandled(true);
				e.getPickedNode().moveToFront();
			}
			
			protected void drag(PInputEvent e) {
				super.drag(e);
				
				ArrayList edges = (ArrayList) e.getPickedNode().getAttribute("edges");
				for (int i = 0; i < edges.size(); i++) {
					NetGraph.this.updateEdge((PPath) edges.get(i));
				}
			}
		}); 
	}
	
	
	
	
	public void updateEdge(PPath edge) {
		// Note that the node's "FullBounds" must be used (instead of just the "Bound") 
		// because the nodes have non-identity transforms which must be included when
		// determining their position.

		PNode node1 = (PNode) ((ArrayList)edge.getAttribute("nodes")).get(0);
		PNode node2 = (PNode) ((ArrayList)edge.getAttribute("nodes")).get(1);
		Point2D start = node1.getFullBoundsReference().getCenter2D();
		Point2D end = node2.getFullBoundsReference().getCenter2D();
		edge.reset();
		edge.moveTo((float)start.getX(), (float)start.getY());
		edge.lineTo((float)end.getX(), (float)end.getY());
	}
}